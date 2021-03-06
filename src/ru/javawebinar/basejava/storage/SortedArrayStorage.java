package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> COMPARATOR_BY_UUID = Comparator.comparing(Resume::getUuid);

    /**
     * @return index of Resume in storage if it exists or '< 0'
     */
    @Override
    protected final Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, COMPARATOR_BY_UUID);
    }

    @Override
    protected final void insertElement(int index, Resume resume) {
        index = Math.abs(index + 1);
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected final void extractElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}
