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
        doSave(resume, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        doRemove(searchKey, uuid);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isNotExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(searchKey, uuid);
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doRemove(Object searchKey, String uuid);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey, String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract boolean isNotExist(Object searchKey);
}
