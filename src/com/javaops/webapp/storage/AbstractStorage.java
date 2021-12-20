package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = ((o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getExistingSearchKey(uuid);
        doSave(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        doRemove(searchKey);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Object searchKey = getNotExistingSearchKey(uuid);
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getNotExistingSearchKey(uuid);
        return doGet(searchKey, uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(doGetAll());
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doRemove(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey, String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume[] doGetAll();
}
