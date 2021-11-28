package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

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
    protected final void saveByIndex(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected final Resume getByIndex(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected void updateByIndex(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected final void deleteByIndex(int index, String uuid) {
        storage.remove(index);
    }

    /**
     * @return index of Resume in storage if it exists or '-1'
     */
    @Override
    protected final int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
