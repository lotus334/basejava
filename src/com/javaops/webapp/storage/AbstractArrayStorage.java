package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void setElement(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getElement(int index, String uuid) {
        return storage[index];
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        index = getIndexOfNewElement(index);
        insertNewElement(index, resume);
        storage[index] = resume;
        size++;
    }

    @Override
    protected void removeElement(int index, String uuid) {
        removeElementFromStorage(index);
        size--;
        storage[size] = null;
    }

    protected abstract int getIndexOfNewElement(int index);

    protected abstract void insertNewElement(int index, Resume resume);

    protected abstract void removeElementFromStorage(int index);
}