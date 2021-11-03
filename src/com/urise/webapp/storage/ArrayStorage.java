package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
                return;
            }
        }
        printIsPresentError(false, resume.getUuid());
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("ERROR: Storage is full, you cannot save resume with 'uuid = " + resume.getUuid() + "'!");
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    printIsPresentError(true, resume.getUuid());
                    return;
                }
            }
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        printIsPresentError(false, uuid);
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        printIsPresentError(false, uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private void printIsPresentError(boolean isPresent, String uuid) {
        System.out.println(isPresent ? "ERROR: Resume with 'uuid = " + uuid + "' already exists in storage!"
                : "ERROR: No resume with 'uuid = " + uuid + "' in storage!");
    }
}
