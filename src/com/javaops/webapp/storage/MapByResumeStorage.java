package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByResumeStorage extends AbstractMapStorage<Resume> {

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
}
