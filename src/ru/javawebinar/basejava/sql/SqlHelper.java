package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sqlRequest) {
        execute(sqlRequest, PreparedStatement::execute);
    }

    public <T> T execute(String sqlRequest, SqlExecutor<T> sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sqlRequest)) {
            return sqlExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void doSaveOrUpdate(Connection connection, String sqlRequest1, String sqlRequest2, Resume resume)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest1)) {
            preparedStatement.setString(1, resume.getFullName());
            String uuid = resume.getUuid();
            preparedStatement.setString(2, uuid);
            ExceptionUtil.isExist(preparedStatement, uuid);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest2)) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, e.getValue());
                preparedStatement.setString(2, resume.getUuid());
                preparedStatement.setString(3, e.getKey().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
