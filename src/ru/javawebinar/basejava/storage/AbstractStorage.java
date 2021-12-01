package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume resume) {
        saveBy(getKey(resume.getUuid(), true), resume);
    }

    protected abstract void saveBy(Object searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        return getBy(getKey(uuid, false));
    }

    protected abstract Resume getBy(Object searchKey);

    @Override
    public final void update(Resume resume) {
        updateBy(getKey(resume.getUuid(), false), resume);
    }

    protected abstract void updateBy(Object searchKey, Resume resume);

    public final void delete(String uuid) {
        deleteBy(getKey(uuid, false));
    }

    protected abstract void deleteBy(Object searchKey);

    protected Object getKey(String uuid, boolean isExist) {
        Object searchKey = getKey(uuid);
        if (isExist && isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else if (!isExist && !isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object getKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
