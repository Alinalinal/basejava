package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int getIndex(String uuid);

    @Override
    public final void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        checkExist(true, index, uuid);
        saveByIndex(resume, index);
    }

    protected abstract void saveByIndex(Resume resume, int index);

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        checkExist(false, index, uuid);
        return getByIndex(index);
    }

    protected abstract Resume getByIndex(int index);

    @Override
    public final void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        checkExist(false, index, uuid);
        updateByIndex(resume, index);
    }

    protected abstract void updateByIndex(Resume resume, int index);

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        checkExist(false, index, uuid);
        deleteByIndex(index);
    }

    protected abstract void deleteByIndex(int index);

    private void checkExist(boolean isExist, int index, String uuid) {
        if (isExist && index >= 0) {
            throw new ExistStorageException(uuid);
        } else if (!isExist && index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}
