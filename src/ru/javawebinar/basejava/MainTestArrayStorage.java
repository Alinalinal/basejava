package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.*;

/**
 * Test for your ru.javawebinar.basejava.storage.ArrayStorage&SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    // private static final Storage STORAGE = new ArrayStorage();
    // private static final Storage STORAGE = new SortedArrayStorage();
    private static final Storage STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        STORAGE.save(r1);
        STORAGE.save(r2);
        STORAGE.save(r3);

        STORAGE.save(r3);
        System.out.println();

        System.out.println("Get r1: " + STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + STORAGE.size());
        System.out.println();

        System.out.println("Get dummy: " + STORAGE.get("dummy"));
        System.out.println();
        printAll();

        Resume r4 = new Resume("uuid2");
        STORAGE.update(r4);
        printAll();
        System.out.println("Get r4: " + STORAGE.get(r4.getUuid()));
        System.out.println();

        Resume r5 = new Resume("uuid5");
        STORAGE.update(r5);
        STORAGE.delete(r5.getUuid());

        STORAGE.delete(r1.getUuid());
        printAll();
        STORAGE.clear();
        printAll();
        System.out.println();

        System.out.println("Size: " + STORAGE.size());
        System.out.println();

        STORAGE.save(r1);
        System.out.println(STORAGE.size());
        STORAGE.clear();
        System.out.println();

        Resume resume;
        for (int i = 0; i <= 10000; i++) {
            resume = new Resume("uuid" + (i + 1));
            STORAGE.save(resume);
        }
        System.out.println(STORAGE.size());
        System.out.println();

        STORAGE.delete("uuid10000");
        System.out.println(STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
