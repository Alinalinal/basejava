package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected final void doSave(Object searchKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume resume) {
        map.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected final void doDelete(Object searchKey) {
        map.remove(((Resume) searchKey).getUuid());
    }

    /**
     * @return Resume if it exists in storage or null
     */
    @Override
    protected Resume getSearchKey(String uuid) {
        return map.getOrDefault(uuid, null);
    }
}
