package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByUuidStorage extends AbstractMapStorage {

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(Object resume, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return storage.get(uuid) != null;
    }

    @Override
    protected void doRemove(Object resume) {
        storage.remove(resume);
    }
}
