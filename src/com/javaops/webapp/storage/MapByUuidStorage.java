package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByUuidStorage extends AbstractMapStorage {

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(Resume resume, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return storage.get(resume.getUuid()) != null;
    }

    @Override
    protected void doRemove(Resume resume) {
        storage.remove(resume.getUuid());
    }
}
