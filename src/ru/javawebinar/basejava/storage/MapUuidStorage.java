package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected final void doSave(Object uuid, Resume resume) {
        map.put(uuid.toString(), resume);
    }

    @Override
    protected final Resume doGet(Object uuid) {
        return map.get(uuid.toString());
    }

    @Override
    protected final void doUpdate(Object uuid, Resume resume) {
        map.put(uuid.toString(), resume);
    }

    @Override
    protected final void doDelete(Object uuid) {
        map.remove(uuid.toString());
    }

    /**
     * @return String 'uuid'
     */
    @Override
    protected final String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected final boolean isExist(Object uuid) {
        return map.containsKey(uuid.toString());
    }
}
