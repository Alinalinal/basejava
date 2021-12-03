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
    protected final Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void insertElement(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected final void extractElement(int index) {
        storage[index] = storage[size - 1];
    }
}
