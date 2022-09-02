package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {

    private static final Properties properties = Config.get().getProps();

    public SqlStorageTest() {
        super(new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"),
                properties.getProperty("db.password")));
    }
}
