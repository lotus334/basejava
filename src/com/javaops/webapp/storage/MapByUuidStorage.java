package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapByUuidStorage extends AbstractStorage<String> {
    private static Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void doRemove(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doUpdate(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected Resume[] doGetAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public void clear() {
        storage = new HashMap<>();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
