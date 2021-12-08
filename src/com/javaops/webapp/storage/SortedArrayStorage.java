package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected void insert(Resume resume, int index) {
        if (index < 0) {
            index = - index - 1;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}