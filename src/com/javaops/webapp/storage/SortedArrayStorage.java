package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
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