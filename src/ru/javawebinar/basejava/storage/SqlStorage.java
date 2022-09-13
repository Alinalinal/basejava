package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ExceptionUtil;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
                    preparedStatement.setString(1, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(rs.getString("type")),
                                rs.getString("value"));
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            sqlHelper.doSaveOrUpdate(connection, "INSERT INTO resume (full_name, uuid) VALUES (?,?)",
                    "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)", resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update in DB " + resume);
        sqlHelper.<Void>transactionalExecute(connection -> {
            sqlHelper.doSaveOrUpdate(connection, "" +
                    "UPDATE resume r SET full_name=? " +
                    "WHERE r.uuid=?", "" +
                    "UPDATE contact c SET value=? " +
                    "WHERE c.resume_uuid=? AND c.type=?", resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete from DB " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ExceptionUtil.isExist(preparedStatement, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.transactionalExecute(connection -> {
            List<Resume> resumes = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM resume r " +
                    "ORDER BY full_name, uuid")) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    resumes.add(new Resume(rs.getString("uuid").trim(),
                            rs.getString("full_name").trim()));
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM contact c " +
                    "WHERE resume_uuid=?")) {
                for (Resume resume : resumes) {
                    preparedStatement.setString(1, resume.getUuid());
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        resume.addContact(ContactType.valueOf(rs.getString("type")),
                                rs.getString("value"));
                    }
                }
            }
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
}
