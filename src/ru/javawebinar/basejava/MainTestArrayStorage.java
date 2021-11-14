package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.*;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage&SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    // private static final Storage ARRAY_STORAGE = new ArrayStorage();
    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.save(r3);
        System.out.println();

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println();

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        System.out.println();
        printAll();

        Resume r4 = new Resume("uuid2");
        ARRAY_STORAGE.update(r4);
        printAll();
        System.out.println("Get r4: " + ARRAY_STORAGE.get(r4.getUuid()));
        System.out.println();

        Resume r5 = new Resume("uuid5");
        ARRAY_STORAGE.update(r5);
        ARRAY_STORAGE.delete(r5.getUuid());

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println();

        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println();

        ARRAY_STORAGE.save(r1);
        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        System.out.println();

        Resume resume;
        for (int i = 0; i <= 10000; i++) {
            resume = new Resume("uuid" + (i + 1));
            ARRAY_STORAGE.save(resume);
        }
        System.out.println(ARRAY_STORAGE.size());
        System.out.println();

        ARRAY_STORAGE.delete("uuid10000");
        System.out.println(ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
