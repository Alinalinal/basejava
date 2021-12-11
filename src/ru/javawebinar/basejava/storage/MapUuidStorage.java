package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected final void doSave(String uuid, Resume resume) {
        map.put(uuid, resume);
    }

    @Override
    protected final Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected final void doUpdate(String uuid, Resume resume) {
        map.put(uuid, resume);
    }

    @Override
    protected final void doDelete(String uuid) {
        map.remove(uuid);
    }

    /**
     * @return String 'uuid'
     */
    @Override
    protected final String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected final boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }
}
