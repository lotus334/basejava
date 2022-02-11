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
        String[] list = getListOfFiles();
        for (String name : list) {
            File file = new File(directory.getAbsolutePath(), name);
            file.delete();
        }
    }

    @Override
    public int size() {
        return getListOfFiles().length;
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
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file, String uuid) {
        return doRead(file);
    }

    @Override
    protected void doRemove(File file) {
        file.delete();
    }

    @Override
    protected Resume[] doGetAll() {
        String[] list = getListOfFiles();
        Resume[] resumes = new Resume[list.length];
        for (int i = 0; i < list.length; i++) {
            resumes[i] = doRead(new File(list[i]));
        }
        return resumes;
    }

    private String[] getListOfFiles() {
        String[] list = directory.list();
        if (list != null) {
            return list;
        }
        return new String[]{};
    }

    protected abstract Resume doRead(File file);

    protected abstract void doWrite(Resume r, File file) throws IOException;
}