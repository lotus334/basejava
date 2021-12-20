package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = ((o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));

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
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }

    @Override
    protected void doRemove(Object searchKey, String uuid) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected Resume doGet(Object searchKey, String uuid) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public List<Resume> getAllSorted() {
        storage.sort(RESUME_COMPARATOR);
        return storage;
    }
}