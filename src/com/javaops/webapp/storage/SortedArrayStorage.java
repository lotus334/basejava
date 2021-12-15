package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) throws StorageException {
        checkOverflow(resume.getUuid());
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
        size++;
    }

    @Override
    protected void removeElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        size--;
        storage[size] = null;
    }

    @Override
    protected int getIndexOfElement(String uuid) {
        String[] ids = new String[size];
        for (int i = 0; i < size; i++) {
            ids[i] = storage[i].getUuid();
        }
        return Arrays.binarySearch(ids, 0, size, uuid);
    }
}