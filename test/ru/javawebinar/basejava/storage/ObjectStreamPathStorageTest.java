package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.ObjectStreamStrategy;

import java.io.IOException;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() throws IOException {
        super(new PathStorage(STORAGE_DIR.getCanonicalPath(), new ObjectStreamStrategy()));
    }
}
