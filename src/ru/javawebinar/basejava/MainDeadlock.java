package ru.javawebinar.basejava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainDeadlock {

    private static final Lock LOCK_1 = new ReentrantLock();
    private static final Lock LOCK_2 = new ReentrantLock();

    public static void main(String[] args) {
        deadLock(LOCK_1, LOCK_2);
        deadLock(LOCK_2, LOCK_1);
    }

    private static void deadLock(Lock lock1, Lock lock2) {
        new Thread(() -> {
            tryGetLock(lock1);
            try {
                Thread.sleep(50);
                tryGetLock(lock2);
                lock2.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
            }
        }).start();
    }

    private static void tryGetLock(Lock lock) {
        System.out.println(Thread.currentThread().getName() + " trying to get lock on " + lock);
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " get lock on " + lock);
    }
}
