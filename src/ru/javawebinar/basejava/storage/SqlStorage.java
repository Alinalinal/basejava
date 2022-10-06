package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.ExceptionUtil;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("CLear DB");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get from DB " + uuid);
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume = sqlHelper.execute("" +
                            "SELECT * FROM resume r " +
                            "LEFT JOIN contact c " +
                            "ON r.uuid = c.resume_uuid " +
                            "WHERE r.uuid=?",
                    preparedStatement -> {
                        fillInPreparedStatement(preparedStatement, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        Resume res = new Resume(uuid, resultSet.getString("full_name"));
                        do {
                            doAddContact(res, resultSet);
                        } while (resultSet.next());
                        return res;
                    });
            sqlHelper.<Void>execute(connection, "" +
                    "SELECT * FROM section s " +
                    "WHERE resume_uuid=?", preparedStatement -> {
                doAddSection(preparedStatement, resume);
                return null;
            });
            return resume;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            sqlHelper.execute(connection, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                    preparedStatement -> {
                        fillInPreparedStatement(preparedStatement, resume.getUuid(), resume.getFullName());
                        return preparedStatement.execute();
                    });
            doInsertContacts(connection, resume);
            doInsertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update in DB " + resume);
        String uuid = resume.getUuid();
        sqlHelper.<Void>transactionalExecute(connection -> {
            sqlHelper.<Void>execute(connection, "" +
                    "UPDATE resume SET full_name=? " +
                    "WHERE uuid=?", preparedStatement -> {
                fillInPreparedStatement(preparedStatement, resume.getFullName(), uuid);
                ExceptionUtil.isExist(preparedStatement, uuid);
                return null;
            });
            doDeleteContacts(connection, uuid);
            doDeleteSections(connection, uuid);
            doInsertContacts(connection, resume);
            doInsertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete from DB " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            fillInPreparedStatement(preparedStatement, uuid);
            ExceptionUtil.isExist(preparedStatement, uuid);
            return null;
        });
    }

    /*
    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            sqlHelper.<Void>execute("" +
                    "SELECT * FROM resume r " +
                    "LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                    "ORDER BY full_name, uuid", preparedStatement -> {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    Resume resume = map.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, resultSet.getString("full_name"));
                        map.put(uuid, resume);
                    }
                    doGetContact(resume, resultSet);
                }
                return null;
            });
            sqlHelper.execute(connection, "" +
                    "SELECT * FROM section " +
                    "WHERE resume_uuid=?", preparedStatement -> {
                for (Map.Entry<String, Resume> e : map.entrySet()) {
                    doGetSection(preparedStatement, e.getValue());
                }
                return null;
            });
            return new ArrayList<>(map.values());
        });
    }
    */

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> resumes = new ArrayList<>();
            sqlHelper.<Void>execute(connection, "" +
                    "SELECT * FROM resume r " +
                    "ORDER BY full_name, uuid", preparedStatement -> {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resumes.add(new Resume(resultSet.getString("uuid").trim(),
                            resultSet.getString("full_name").trim()));
                }
                return null;
            });
            sqlHelper.execute(connection, "" +
                    "SELECT * FROM contact " +
                    "WHERE resume_uuid=?", preparedStatement -> {
                for (Resume resume : resumes) {
                    fillInPreparedStatement(preparedStatement, resume.getUuid());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        if (resultSet.getString("resume_uuid") != null) {
                            doAddContact(resume, resultSet);
                        }
                    }
                }
                return null;
            });
            sqlHelper.execute(connection, "" +
                    "SELECT * FROM section " +
                    "WHERE resume_uuid=?", preparedStatement -> {
                for (Resume resume : resumes) {
                    doAddSection(preparedStatement, resume);
                }
                return null;
            });
            return resumes;
        });
    }

    @Override
    public int size() {
        LOG.info("Get size of DB");
        return sqlHelper.execute("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private static void fillInPreparedStatement(PreparedStatement preparedStatement, String... strings)
            throws SQLException {
        int count = 1;
        for (String string : strings) {
            preparedStatement.setString(count++, string);
        }
    }

    private static void doAddContact(Resume resume, ResultSet resultSet) throws SQLException {
        if (resultSet.getString("resume_uuid") != null) {
            resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                    resultSet.getString("value"));
        }
    }

    private static void doAddSection(PreparedStatement preparedStatement, Resume resume) throws SQLException {
        fillInPreparedStatement(preparedStatement, resume.getUuid());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("resume_uuid") != null) {
                SectionType type = SectionType.valueOf(resultSet.getString("type"));
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(type, new TextSection(resultSet.getString("value")));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String value = resultSet.getString("value");
                        List<String> list = new ArrayList<>(Arrays.asList(value.split("\n")));
                        resume.addSection(type, new ListSection(list));
                        break;
                }
            }
        }
    }

    private void doInsertContacts(Connection connection, Resume resume) {
        sqlHelper.execute(connection, "" +
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", preparedStatement -> {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                String value = e.getValue();
                if (value != null && !value.trim().equals("")) {
                    fillInPreparedStatement(preparedStatement, resume.getUuid(), e.getKey().name(), value);
                    preparedStatement.addBatch();
                }
            }
            return preparedStatement.executeBatch();
        });
    }

    private void doInsertSections(Connection connection, Resume resume) {
        sqlHelper.execute(connection, "" +
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)", preparedStatement -> {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                AbstractSection section = e.getValue();
                if (section != null) {
                    SectionType type = e.getKey();
                    String value;
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            value = ((TextSection) section).getContent();
                            fillInPreparedStatement(preparedStatement, resume.getUuid(), e.getKey().name(), value);
                            preparedStatement.addBatch();
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            value = String.join("\n", ((ListSection) section).getContent());
                            fillInPreparedStatement(preparedStatement, resume.getUuid(), e.getKey().name(), value);
                            preparedStatement.addBatch();
                            break;
                    }
                }
            }
            return preparedStatement.executeBatch();
        });
    }

    private void doDeleteContacts(Connection connection, String uuid) {
        sqlHelper.execute(connection, "" +
                "DELETE FROM contact " +
                "WHERE resume_uuid=?", preparedStatement -> {
            fillInPreparedStatement(preparedStatement, uuid);
            return preparedStatement.execute();
        });
    }

    private void doDeleteSections(Connection connection, String uuid) {
        sqlHelper.execute(connection, "" +
                "DELETE FROM section " +
                "WHERE resume_uuid=?", preparedStatement -> {
            fillInPreparedStatement(preparedStatement, uuid);
            return preparedStatement.execute();
        });
    }
}
