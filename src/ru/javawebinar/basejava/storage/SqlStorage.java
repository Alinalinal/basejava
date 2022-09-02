package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;
import ru.javawebinar.basejava.sql.SqlExecutor;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        new SqlHelper(connectionFactory).preparedSqlRequest("CLear DB", "DELETE FROM resume",
                PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return new SqlHelper(connectionFactory).preparedSqlRequest("Get from DB " + uuid,
                "SELECT * FROM resume r WHERE r.uuid=?", (SqlExecutor<Resume>) preparedStatement -> {
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
        new SqlHelper(connectionFactory).preparedSqlRequest("Save in DB " + resume,
                "INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    try {
                        return preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new ExistStorageException(resume.getUuid());
                    }
                });
    }

    @Override
    public void update(Resume resume) {
        new SqlHelper(connectionFactory).preparedSqlRequest("Update in DB " + resume,
                "UPDATE resume r SET full_name=? WHERE r.uuid=?", preparedStatement -> {
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
        new SqlHelper(connectionFactory).preparedSqlRequest("Delete from DB " + uuid,
                "DELETE FROM resume WHERE uuid=?", preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return new SqlHelper(connectionFactory).preparedSqlRequest("GetAllSorted from DB",
                "SELECT * FROM resume", preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        throw new StorageException("Database is empty!");
                    }
                    List<Resume> resumeList = new ArrayList<>();
                    resumeList.add(new Resume(rs.getString("uuid").trim(),
                            rs.getString("full_name").trim()));
                    while (rs.next()) {
                        resumeList.add(new Resume(rs.getString("uuid").trim(),
                                rs.getString("full_name").trim()));
                    }
                    return resumeList;
                });
    }

    @Override
    public int size() {
        return new SqlHelper(connectionFactory).preparedSqlRequest("Get size of DB",
                "SELECT count(*) FROM resume", preparedStatement -> {
                    ResultSet rs = preparedStatement.executeQuery();
                    rs.next();
                    return rs.getInt(1);
                });
    }
}
