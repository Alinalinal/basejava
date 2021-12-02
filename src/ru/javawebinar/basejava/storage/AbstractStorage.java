package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        saveBy(searchKey, resume);
    }

    protected abstract void saveBy(Object searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        return getBy(getSearchKeyIfResumeExist(uuid));
    }

    protected abstract Resume getBy(Object searchKey);

    @Override
    public final void update(Resume resume) {
        updateBy(getSearchKeyIfResumeExist(resume.getUuid()), resume);
    }

    protected abstract void updateBy(Object searchKey, Resume resume);

    @Override
    public final void delete(String uuid) {
        deleteBy(getSearchKeyIfResumeExist(uuid));
    }

    protected abstract void deleteBy(Object searchKey);

    protected Object getSearchKeyIfResumeExist(String uuid) {
        Object searchKey = getKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object getKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
