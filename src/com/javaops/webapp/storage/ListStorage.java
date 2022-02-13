package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected void doRemove(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected void doUpdate(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }

    @Override
    public Resume[] doGetAll() {
        return storage.toArray(new Resume[0]);
    }
}