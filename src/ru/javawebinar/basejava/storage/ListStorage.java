package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {

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
    protected final void doSave(Integer searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected final Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected final void doUpdate(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        list.remove(searchKey.intValue());
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
    protected final boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected final List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }
}
