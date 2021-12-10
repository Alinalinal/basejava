package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    public final void clear() {
        map.clear();
    }

    @Override
    public final int size() {
        return map.size();
    }

    @Override
    protected final List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }
}
