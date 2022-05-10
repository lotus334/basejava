package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {

    protected static Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    protected void doClear() {
        storage.clear();
    }
}
