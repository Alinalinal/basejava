package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    public final void clear() {
        list.clear();
    }

    @Override
    public final int size() {
        return list.size();
    }

    @Override
    protected final void doSave(Object searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected final Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume resume) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected final void doDelete(Object searchKey) {
        list.remove((int) searchKey);
    }

    /**
     * @return index of Resume in storage if it exists or '-1'
     */

    @Override
    protected final Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected final List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }
}
