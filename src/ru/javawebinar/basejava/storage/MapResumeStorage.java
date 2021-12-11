package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected final void doSave(Resume searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final Resume doGet(Resume searchResume) {
        return searchResume;
    }

    @Override
    protected final void doUpdate(Resume searchResume, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected final void doDelete(Resume searchResume) {
        map.remove(searchResume.getUuid());
    }

    /**
     * @return Resume if it exists in storage or null
     */
    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected final boolean isExist(Resume searchResume) {
        return searchResume != null;
    }
}
