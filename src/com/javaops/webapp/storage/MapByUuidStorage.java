package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByUuidStorage extends AbstractMapStorage {

    @Override
    protected Resume doGet(Resume resume, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doRemove(Resume resume) {
        storage.remove(resume.getUuid());
    }
}
