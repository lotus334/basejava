package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByUuidStorage extends AbstractMapStorage<String> {

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
}
