package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractMapStorage extends AbstractStorage {

    protected Map<Object, Resume> storage = new TreeMap();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Object resume, Resume r) {
        storage.put(r.getUuid(), r);
    }
}
