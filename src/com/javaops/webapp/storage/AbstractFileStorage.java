package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                doRemove(file);
            }
        }
    }

    @Override
    public int size() {
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            return listFiles.length;
        }
        return 0;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException(file.getName() + " create error", file.getName());
            }
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    @Override
    protected void doRemove(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getName() + " delete error", file.getName());
        }
    }

    @Override
    protected Resume[] doGetAll() {
        File[] listFiles = directory.listFiles();
        Resume[] resumes = new Resume[0];
        if (listFiles != null) {
            resumes = new Resume[listFiles.length];
            for (int i = 0; i < listFiles.length; i++) {
                resumes[i] = doRead(listFiles[i]);
            }
        }
        return resumes;
    }

    protected abstract Resume doRead(File file);

    protected abstract void doWrite(Resume r, File file) throws IOException;
}