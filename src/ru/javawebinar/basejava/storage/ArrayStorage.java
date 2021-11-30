package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @return index of Resume in storage if it exists or '-1'
     */
    @Override
    protected final Object getKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void insertBy(int searchKey, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected final void extractBy(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}
