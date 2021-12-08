package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.*;

import java.util.List;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage&SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    // private static final Storage STORAGE = new ArrayStorage();
    // private static final Storage STORAGE = new SortedArrayStorage();
    // private static final Storage STORAGE = new ListStorage();
    // private static final Storage STORAGE = new MapUuidStorage();
    private static final Storage STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "A A");
        Resume r2 = new Resume("uuid2", "B B");
        Resume r3 = new Resume("uuid3", "C C");

        STORAGE.save(r1);
        STORAGE.save(r2);
        STORAGE.save(r3);
        printAll();

        // STORAGE.save(r3); // must throw ExistStorageException
        System.out.println();

        System.out.println("Get r1: " + STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + STORAGE.size());
        System.out.println();

        // System.out.println("Get dummy: " + STORAGE.get("dummy")); // must throw NotExistStorageException
        System.out.println();
        printAll();

        Resume r4 = new Resume("uuid2", "D D");
        STORAGE.update(r4);
        printAll();
        System.out.println("Get r4: " + STORAGE.get(r4.getUuid()));
        System.out.println();

        Resume r5 = new Resume("uuid5", "E E");
        // STORAGE.update(r5); // must throw NotExistStorageException
        // STORAGE.delete(r5.getUuid()); // must throw NotExistStorageException

        STORAGE.delete(r1.getUuid());
        printAll();
        STORAGE.clear();
        printAll();
        System.out.println();

        System.out.println("Size: " + STORAGE.size());
        System.out.println();

        STORAGE.save(r1);
        System.out.println("Size: " + STORAGE.size());
        STORAGE.clear();
        System.out.println();

        Resume resume;
        for (int i = 0; i < 10000; i++) {
            resume = new Resume("uuid" + (i + 1), "fullName" + i);
            STORAGE.save(resume);
        }
        System.out.println(STORAGE.size());
        System.out.println();

        STORAGE.delete("uuid10000");
        System.out.println(STORAGE.size());

        STORAGE.save(new Resume("uuid10000", "fullName10000"));
        STORAGE.save(new Resume("uuid10001", "fullName10001")); // must throw StorageException for ArrayStorage & SortedArrayStorage
    }

    static void printAll() {
        System.out.println("\nGet All Sorted");
        List<Resume> sortedStorage = STORAGE.getAllSorted();
        if (sortedStorage.size() == 0) {
            System.out.println("Empty Storage!");
        } else {
            for (Resume r : STORAGE.getAllSorted()) {
                System.out.println(r);
            }
        }
    }
}
