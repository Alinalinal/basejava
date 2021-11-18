package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resumeOld = storage.get(UUID_1);
        storage.update(new Resume(UUID_1));
        assertNotSame(resumeOld, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws NotExistStorageException {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void getAll() {
        assertEquals(3, storage.getAll().length);
    }

    @Test
    public void save() {
        Resume resume = new Resume("dummy");
        storage.save(resume);
        assertEquals(4, storage.size());
        assertSame(resume, storage.get("dummy"));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws StorageException {
        for (int i = storage.size(); i < 10000; i++) {
            try {
                storage.save(new Resume("uuid" + (i + 1)));
            } catch (StorageException e) {
                fail("Storage overflow occurred ahead of time");
            }
        }
        storage.save(new Resume("dummy"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws ExistStorageException {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.delete(UUID_2);
        assertEquals(1, storage.size());
        storage.delete(UUID_3);
        assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws NotExistStorageException {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get("dummy");
    }
}