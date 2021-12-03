package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public final void clear() {
        map.clear();
    }

    @Override
    public final Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public final int size() {
        return map.size();
    }

    @Override
    protected final void doSave(Object searchKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume doGet(Object searchKey) {
        return map.get(searchKey.toString());
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume resume) {
        map.put(searchKey.toString(), resume);
    }

    @Override
    protected final void doDelete(Object searchKey) {
        map.remove(searchKey.toString());
    }

    /**
     * @return String uuid if Resume exists in storage or null
     */
    @Override
    protected final Object getSearchKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
