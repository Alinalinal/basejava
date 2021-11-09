package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insert(Resume resume, int index) {
        index = Math.abs(index + 1);
        Resume[] partOfStorage = Arrays.copyOfRange(storage, index, size);
        storage[index] = resume;
        System.arraycopy(partOfStorage, 0, storage, (index + 1), partOfStorage.length);
        size++;
    }

    @Override
    protected void extract(int index) {
        Resume[] partOfStorage = Arrays.copyOfRange(storage, (index + 1), size);
        System.arraycopy(partOfStorage, 0, storage, index, partOfStorage.length);
        storage[size - 1] = null;
        size--;
    }
}
