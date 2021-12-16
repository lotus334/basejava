package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected int getIndexOfElement(String uuid) {
        if (storage.get(uuid) != null) {
            return 1;
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void removeElement(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void setElement(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(int index, String uuid) {
        return storage.get(uuid);
    }
}
