package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        String uuid = resume.getUuid();
        SK searchKey = getExistentSearchKey(uuid);
        doSave(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getNotExistentSearchKey(uuid);
        doRemove(searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        String uuid = resume.getUuid();
        SK searchKey = getNotExistentSearchKey(uuid);
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getNotExistentSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = Arrays.asList(doGetAll());
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    private SK getExistentSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistentSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doRemove(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract Resume[] doGetAll();
}
