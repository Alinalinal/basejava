package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SqlHelper {

    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T preparedSqlRequest(String logMsg, String sqlRequest, SqlExecutor sqlExecutor) {
        LOG.info(logMsg);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return (T) sqlExecutor.executeSqlRequest(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
