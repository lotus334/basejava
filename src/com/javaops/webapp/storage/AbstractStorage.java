package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertElement(resume, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        removeElement(searchKey, uuid);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        setElement(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(searchKey, uuid);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void insertElement(Resume resume, Object searchKey);

    protected abstract void removeElement(Object searchKey, String uuid);

    protected abstract void setElement(Object searchKey, Resume resume);

    protected abstract Resume getElement(Object searchKey, String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract boolean isNotExist(Object searchKey);
}
