package ru.javawebinar.basejava.storage;

public class ObjectStreamFileStorageTest extends FileStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
