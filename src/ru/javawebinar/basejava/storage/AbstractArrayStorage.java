package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final int size() {
        return size;
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            printErrorMessage(false, uuid);
            return null;
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            printErrorMessage(false, resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public final void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: Storage is full, you cannot save resume with 'uuid = " + resume.getUuid() + "'!");
        } else {
            int index = getIndex(resume.getUuid());
            if (getIndex(resume.getUuid()) >= 0) {
                printErrorMessage(true, resume.getUuid());
            } else {
                insert(resume, index);
                size++;
            }
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            printErrorMessage(false, uuid);
        } else {
            extractResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    private void printErrorMessage(boolean isPresent, String uuid) {
        System.out.println(isPresent ? "ERROR: Resume with 'uuid = " + uuid + "' already exist in storage!"
                : "ERROR: Resume with 'uuid = " + uuid + "' not exist in storage!");
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);

    protected abstract void extractResume(int index);
}
