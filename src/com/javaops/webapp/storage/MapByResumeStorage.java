package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByResumeStorage extends AbstractMapStorage {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Resume resume, String uuid) {
        return resume;
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected void doRemove(Resume resume) {
        storage.remove(resume.getUuid());
    }
}
