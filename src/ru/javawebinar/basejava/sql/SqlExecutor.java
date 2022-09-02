package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T> {

    T executeSqlRequest(PreparedStatement preparedStatement) throws SQLException;
}
