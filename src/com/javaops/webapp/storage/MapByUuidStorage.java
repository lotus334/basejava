package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByUuidStorage extends AbstractMapStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid) == null ? null : uuid;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doRemove(Object searchKey, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }
}
