package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private ObjectStreamStorageInterface objectStreamStorage;

    protected FileStorage(File directory, ObjectStreamStorageInterface objectStreamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(objectStreamStorage, "objectStreamStorage must not be ull");
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
        File[] listFiles = directory.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                doRemove(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list != null) {
            return list.length;
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
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName());
        }
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
        if (listFiles == null) {
            throw new StorageException("Directory read error", null);
        }
        Resume[] resumes = new Resume[listFiles.length];
        for (int i = 0; i < listFiles.length; i++) {
            resumes[i] = doGet(listFiles[i]);
        }
        return resumes;
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return objectStreamStorage.doRead(inputStream);
    }

    protected void doWrite(Resume r, OutputStream outputStream) throws IOException {
        objectStreamStorage.doWrite(r, outputStream);
    }
}