package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.strategy.Strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private Strategy objectStreamStorage;

    protected FileStorage(File directory, Strategy objectStreamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(objectStreamStorage, "objectStreamStorage must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.objectStreamStorage = objectStreamStorage;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            doRemove(file);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            objectStreamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file" + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return objectStreamStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doRemove(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume[] doGetAll() {
        File[] listFiles = getFiles();
        List<Resume> resumes = new ArrayList<>();
        for (File file : listFiles) {
            resumes.add(doGet(file));
        }
        return resumes.toArray(new Resume[0]);
    }

    private File[] getFiles() {
        return directory.listFiles();
    }
}