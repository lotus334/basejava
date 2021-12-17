package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected void removeElement(Object searchKey, String uuid) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected void setElement(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected Resume getElement(Object searchKey, String uuid) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected boolean isNotExist(Object searchKey) {
        return searchKey == null;
    }
}