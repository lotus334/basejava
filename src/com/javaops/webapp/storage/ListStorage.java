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
    protected int getIndexOfElement(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void removeElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void setElement(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }
}