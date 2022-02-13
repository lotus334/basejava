package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
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
        return searchKey;
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}
