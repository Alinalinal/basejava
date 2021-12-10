package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected final void doSave(Object searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume doGet(Object searchResume) {
        return (Resume) searchResume;
    }

    @Override
    protected final void doUpdate(Object searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final void doDelete(Object searchResume) {
        map.remove(((Resume) searchResume).getUuid());
    }

    /**
     * @return Resume if it exists in storage or null
     */
    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected final boolean isExist(Object searchResume) {
        return searchResume != null;
    }
}
