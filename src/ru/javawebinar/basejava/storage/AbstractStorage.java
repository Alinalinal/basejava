package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> COMPARATOR_BY_FULL_NAME_THEN_UUID =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public final void save(Resume resume) {
        doSave(getNotExistedSearchKey(resume.getUuid()), resume);
    }

    @Override
    public final Resume get(String uuid) {
        return doGet(getExistedSearchKey(uuid));
    }

    @Override
    public final void update(Resume resume) {
        doUpdate(getExistedSearchKey(resume.getUuid()), resume);
    }

    @Override
    public final void delete(String uuid) {
        doDelete(getExistedSearchKey(uuid));
    }

    /**
     * @return list, sorted by 'fullName' then by 'uuid', contains only Resumes in storage (without null)
     */
    public final List<Resume> getAllSorted() {
        List<Resume> resultList = doCopyAll();
        resultList.sort(COMPARATOR_BY_FULL_NAME_THEN_UUID);
        return resultList;
    }

    protected final SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected final SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
