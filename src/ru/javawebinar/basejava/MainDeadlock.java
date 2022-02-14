package ru.javawebinar.basejava;

public class MainDeadlock {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            doDeadlock(LOCK_1, LOCK_2);
        });
        thread.start();

        doDeadlock(LOCK_2, LOCK_1);
    }

    private static void doDeadlock(Object lock1, Object lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("Success!");
            }
        }
    }
}
