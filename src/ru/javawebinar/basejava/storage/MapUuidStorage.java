package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

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
    protected final String getSearchKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null;
    }
}
