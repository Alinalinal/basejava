package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    protected final void saveBy(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected final Resume getBy(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected final void updateBy(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected final void deleteBy(Object searchKey) {
        storage.remove((int) searchKey);
    }

    /**
     * @return index of Resume in storage if it exists or '-1'
     */
    @Override
    protected final Object getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
