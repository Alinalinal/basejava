package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getKey(String uuid);

    @Override
    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getKey(uuid);
        if ((searchKey instanceof Integer && (Integer) searchKey >= 0) || searchKey instanceof String) {
            throw new ExistStorageException(uuid);
        }
        saveBy(searchKey, resume);
    }

    protected abstract void saveBy(Object searchKey, Resume resume);

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getKey(uuid);
        if (searchKey instanceof Integer && (Integer) searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getBy(searchKey);
    }

    protected abstract Resume getBy(Object searchKey);

    @Override
    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getKey(uuid);
        if (searchKey instanceof Integer && ((Integer) searchKey) < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateBy(searchKey, resume);
    }

    protected abstract void updateBy(Object searchKey, Resume resume);

    public final void delete(String uuid) {
        Object searchKey = getKey(uuid);
        if (searchKey instanceof Integer && ((Integer) searchKey) < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteBy(searchKey);
    }

    protected abstract void deleteBy(Object searchKey);
}
