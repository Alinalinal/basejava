package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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

    @Override
    protected final void doSave(Integer index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", resume.getUuid());
        }
        insertElement(index, resume);
        size++;
    }

    @Override
    protected final Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected final void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected final void doDelete(Integer index) {
        extractElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected final List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void extractElement(int index);
}
