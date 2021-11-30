package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * @return index of Resume in storage if it exists or '< 0'
     */
    @Override
    protected final Object getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected final void insertBy(int searchKey, Resume resume) {
        searchKey = Math.abs(searchKey + 1);
        System.arraycopy(storage, searchKey, storage, searchKey + 1, size - searchKey);
        storage[searchKey] = resume;
    }

    @Override
    protected final void extractBy(int searchKey) {
        int numMoved = size - searchKey - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, numMoved);
        }
    }
}
