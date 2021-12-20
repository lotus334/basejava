package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

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
    protected Resume doGet(Object searchKey, String uuid) {
        return storage.get(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        return new ArrayList<>(storage.values());
    }
}
