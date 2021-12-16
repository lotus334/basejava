package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class MapStorage extends AbstractStorage {
    @Override
    protected int getIndexOfElement(String uuid) {
        return 0;
    }

    @Override
    protected void insertElement(Resume resume, int index) {

    }

    @Override
    protected void removeElement(int index) {

    }

    @Override
    protected void setElement(int index, Resume resume) {

    }

    @Override
    protected Resume getElement(int index) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}
