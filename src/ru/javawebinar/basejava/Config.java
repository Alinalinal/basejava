package ru.javawebinar.basejava;

import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Config {
    protected static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Storage sqlStorage;
    private final Set<String> immutableUuids = new HashSet<>() {{  // for JDK 9+: Set.of("111", "222");
        add("11111111-1111-1111-1111-111111111111");
        add("22222222-2222-2222-2222-222222222222");
    }};

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"),
                    props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }

    public boolean isImmutable(String uuid) {
        return immutableUuids.contains(uuid);
    }

    public void checkImmutable(String uuid) {
        if (immutableUuids.contains(uuid)) {
            throw new RuntimeException("Зарезервированные резюме нельзя менять");
        }
    }
}
