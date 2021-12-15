package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume resume, int index) {
        checkOverflow(resume.getUuid());
        storage[size] = resume;
        size++;
    }

    @Override
    protected void removeElement(int index) {
        storage[index] = storage[size - 1];
        size--;
        storage[size] = null;
    }

    @Override
    protected int getIndexOfElement(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}