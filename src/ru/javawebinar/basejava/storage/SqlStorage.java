package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ExceptionUtil;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

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
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "WHERE r.uuid=?",
                preparedStatement -> {
                    fillInPreparedStatement(preparedStatement, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    doGetContacts(resume, rs);
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
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            sqlHelper.<Void>execute(connection, "" +
                    "UPDATE resume r SET full_name=? " +
                    "WHERE r.uuid=?", preparedStatement -> {
                fillInPreparedStatement(preparedStatement, resume.getFullName(), uuid);
                ExceptionUtil.isExist(preparedStatement, uuid);
                return null;
            });
            sqlHelper.execute(connection, "" +
                    "DELETE FROM contact WHERE resume_uuid=?", preparedStatement -> {
                fillInPreparedStatement(preparedStatement, uuid);
                return preparedStatement.execute();
            });
            doInsertContacts(connection, resume);
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

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> resumes = new ArrayList<>();
            sqlHelper.<Void>execute(connection, "" +
                    "SELECT * FROM resume r " +
                    "ORDER BY full_name, uuid", preparedStatement -> {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid").trim(),
                            rs.getString("full_name").trim()));
                }
                return null;
            });
            sqlHelper.execute(connection, "" +
                    "SELECT * FROM contact c " +
                    "WHERE resume_uuid=?", preparedStatement -> {
                for (Resume resume : resumes) {
                    fillInPreparedStatement(preparedStatement, resume.getUuid());
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    doGetContacts(resume, rs);
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

    private static void doGetContacts(Resume resume, ResultSet resultSet) throws SQLException {
        do {
            if (resultSet.getString("resume_uuid") != null) {
                resume.addContact(ContactType.valueOf(resultSet.getString("type")),
                        resultSet.getString("value"));
            }
        } while (resultSet.next());
    }

    private void doInsertContacts(Connection connection, Resume resume) {
        sqlHelper.execute(connection, "" +
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", preparedStatement -> {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                String value = e.getValue();
                if (value != null || value.trim().equals("")) {
                    fillInPreparedStatement(preparedStatement, resume.getUuid(), e.getKey().name(), value);
                    preparedStatement.addBatch();
                }
            }
            return preparedStatement.executeBatch();
        });
    }
}
