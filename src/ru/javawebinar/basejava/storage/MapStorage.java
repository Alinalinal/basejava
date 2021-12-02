package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    public final Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    protected final void saveBy(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume getBy(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    protected final void updateBy(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected final void deleteBy(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    /**
     * @return String uuid if Resume exists in storage or null
     */
    @Override
    protected final Object getKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
