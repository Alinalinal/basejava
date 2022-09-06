package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlExecutor;
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
        sqlHelper.preparedSqlRequest("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get from DB " + uuid);
        return sqlHelper.preparedSqlRequest("SELECT * FROM resume r WHERE r.uuid=?",
                (SqlExecutor<Resume>) preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save in DB " + resume);
        sqlHelper.preparedSqlRequest("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    return preparedStatement.execute();
                });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update in DB " + resume);
        sqlHelper.preparedSqlRequest("UPDATE resume r SET full_name=? WHERE r.uuid=?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete from DB " + uuid);
        sqlHelper.preparedSqlRequest("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted from DB");
        return sqlHelper.preparedSqlRequest("SELECT * FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.isBeforeFirst()) {
                throw new StorageException("Database is empty!");
            }
            List<Resume> resumesList = new ArrayList<>();
            while (rs.next()) {
                resumesList.add(new Resume(rs.getString("uuid").trim(),
                        rs.getString("full_name").trim()));
            }
            return resumesList;
        });
    }

    @Override
    public int size() {
        LOG.info("Get size of DB");
        return sqlHelper.preparedSqlRequest("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new StorageException("");
            }
            return rs.getInt(1);
        });
    }
}
