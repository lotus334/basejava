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
    protected void setElement(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected Resume getElement(Object index, String uuid) {
        return storage[(Integer) index];
    }

    @Override
    protected void insertElement(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int index = getIndexOfNewElement((Integer) searchKey);
        insertNewElement(index, resume);
        storage[index] = resume;
        size++;
    }

    @Override
    protected void removeElement(Object index, String uuid) {
        removeElementFromStorage((Integer) index);
        size--;
        storage[size] = null;
    }

    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected boolean isNotExist(Object searchKey) {
        return (Integer) searchKey < 0;
    }

    protected abstract int getIndexOfNewElement(int index);

    protected abstract void insertNewElement(int index, Resume resume);

    protected abstract void removeElementFromStorage(int index);

    protected abstract Integer getSearchKey(String uuid);
}