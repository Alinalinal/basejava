package ru.javawebinar.basejava.storage;

public class ObjectStreamPathStorageTest extends PathStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStrategy()));
    }
}
