package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class SqlHelper {

    private static final Properties properties = Config.get().getProps();

    private final ConnectionFactory connectionFactory;

    public SqlHelper() {
        this.connectionFactory = () -> DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.user"), properties.getProperty("db.password"));
    }

    public <T> T preparedSqlRequest(String sqlRequest, SqlExecutor sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            return (T) sqlExecutor.executeSqlRequest(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
