package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapByResumeStorage extends AbstractStorage<Resume> {
    private static Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return new Resume(uuid, uuid);
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void doRemove(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return storage.get(searchKey.getUuid());
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return storage.containsKey(searchKey.getUuid());
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
