package ru.javawebinar.basejava;

public class LazySingleton {

    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    public static synchronized LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
        /*if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    int i = 13;
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;*/
    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
}
