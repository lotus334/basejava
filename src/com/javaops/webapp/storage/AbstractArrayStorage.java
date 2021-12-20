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
    protected void doUpdate(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected Resume doGet(Object index, String uuid) {
        return storage[(Integer) index];
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int index = (Integer) searchKey;
        insertElement(index, resume);
        size++;
    }

    @Override
    protected void doRemove(Object index, String uuid) {
        removeElement((Integer) index);
        size--;
        storage[size] = null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    public Resume[] doGetAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void removeElement(int index);

    protected abstract Integer getSearchKey(String uuid);
}