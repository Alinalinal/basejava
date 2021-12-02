package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
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
    protected final void saveBy(Object searchKey, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        }
        insertBy((int) searchKey, resume);
        size++;
    }

    protected abstract void insertBy(int searchKey, Resume resume);

    @Override
    protected final void deleteBy(Object searchKey) {
        extractBy((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void extractBy(int searchKey);

    @Override
    protected final Resume getBy(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected final void updateBy(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
