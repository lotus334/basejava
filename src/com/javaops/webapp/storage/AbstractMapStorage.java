package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage<Resume> {

    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    public Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume resume, Resume r) {
        storage.put(r.getUuid(), r);
    }
}
