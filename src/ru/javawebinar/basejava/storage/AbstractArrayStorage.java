package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected final void saveByIndex(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        }
        insertByIndex(resume, index);
        size++;
    }

    @Override
    protected final void deleteByIndex(int index) {
        extractByIndex(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    protected final void updateByIndex(Resume resume, int index) {
        storage[index] = resume;
    }

    protected abstract void insertByIndex(Resume resume, int index);

    protected abstract void extractByIndex(int index);
}
