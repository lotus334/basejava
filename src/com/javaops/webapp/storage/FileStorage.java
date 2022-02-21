package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private ObjectStreamStorageInterface objectStreamStorage;

    protected FileStorage(File directory, ObjectStreamStorageInterface objectStreamStorage) {
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
        for (File file : directory.listFiles()) {
            doRemove(file);
        }
    }

    @Override
    public int size() {
        return directory.list().length;
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
        List<Resume> resumes = new ArrayList<>();
        for (File file : listFiles) {
            resumes.add(doGet(file));
        }
        return resumes.toArray(new Resume[0]);
    }

    protected Resume doRead(InputStream inputStream) throws IOException {
        return objectStreamStorage.doRead(inputStream);
    }

    protected void doWrite(Resume r, OutputStream outputStream) throws IOException {
        objectStreamStorage.doWrite(r, outputStream);
    }
}