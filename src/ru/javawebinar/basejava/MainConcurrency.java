package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {

    public static final int THREADS_NUMBER = 10000;
    //private static final Object LOCK = new Object();
    //private static final Lock LOCK = new ReentrantLock();
    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
    private static final Lock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                //throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
                    //counter++;
                }
            }
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //CompletionService completionService = new ExecutorCompletionService(executorService);

        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                //Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    //System.out.println(DATE_FORMAT.get().format(new Date()));
                    System.out.println(LocalDateTime.now().format(FORMATTER));
                }
                latch.countDown();
                return 5;
            });
            //thread.start();
            //threads.add(thread);
        }

        /*
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
         */

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        //System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
        //synchronized (this) {
        //synchronized (MainConcurrency.class) {
        //WRITE_LOCK.lock();
        //try {
        atomicCounter.incrementAndGet();
        //counter++;
        //} finally {
        //    WRITE_LOCK.unlock();
        //}
        //wait();
        //readFile
        //...
        //}
    }
}
