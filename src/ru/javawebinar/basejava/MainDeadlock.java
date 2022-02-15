package ru.javawebinar.basejava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainDeadlock {

    private static final Lock LOCK_1 = new ReentrantLock();
    private static final Lock LOCK_2 = new ReentrantLock();

    public static void main(String[] args) {
        startThread(LOCK_1, LOCK_2);
        startThread(LOCK_2, LOCK_1);
    }

    private static void startThread(Lock lock1, Lock lock2) {
        new Thread(() -> {
            tryLock(lock1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tryLock(lock2);
            lock2.unlock();
            lock1.unlock();
        }).start();
    }

    private static void tryLock(Lock lock) {
        System.out.println(Thread.currentThread().getName() + " trying to get lock on " + lock);
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " get lock on " + lock);
    }
}
