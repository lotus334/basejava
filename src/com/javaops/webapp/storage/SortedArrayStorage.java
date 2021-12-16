package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndexOfElement(String uuid) {
        String[] ids = new String[size];
        for (int i = 0; i < size; i++) {
            ids[i] = storage[i].getUuid();
        }
        return Arrays.binarySearch(ids, 0, size, uuid);
    }

    @Override
    protected int getIndexOfNewElement(int index) {
        return -index - 1;
    }

    @Override
    protected void insertNewElement(int index, Resume resume) {
        System.arraycopy(storage, index, storage, index + 1, size - index);
    }

    @Override
    protected void removeElementFromStorage(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}