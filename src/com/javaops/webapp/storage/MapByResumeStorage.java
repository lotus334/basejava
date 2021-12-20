package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapByResumeStorage extends AbstractMapStorage {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Object resume, String uuid) {
        return (Resume) resume;
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doRemove(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }
}
