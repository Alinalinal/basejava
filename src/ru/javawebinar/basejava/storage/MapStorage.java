package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();

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
    protected final void saveByIndex(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume getByIndex(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected final void updateByIndex(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final void deleteByIndex(int index, String uuid) {
        storage.remove(uuid);
    }

    /**
     * @return '1' if Resume exists in storage or '-1'
     */
    @Override
    protected final int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }
}
