package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndexOfElement(uuid);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertElement(resume, index);
    }

    public void delete(String uuid) {
        int index = getIndexOfElement(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        removeElement(index);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndexOfElement(uuid);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        setElement(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndexOfElement(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    protected abstract int getIndexOfElement(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void removeElement(int index);

    protected abstract void setElement(int index, Resume resume);

    protected abstract Resume getElement(int index);
}
