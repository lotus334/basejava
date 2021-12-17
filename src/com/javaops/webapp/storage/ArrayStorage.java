package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int getIndexOfNewElement(int index) {
        return size;
    }

    @Override
    protected void insertNewElement(int index, Resume resume) {}

    @Override
    protected void removeElementFromStorage(int index) {
        storage[index] = storage[size - 1];
    }
}