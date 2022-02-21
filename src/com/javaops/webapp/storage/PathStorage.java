package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;

    private ObjectStreamStorageInterface objectStreamStorage;

    protected PathStorage(String dir, ObjectStreamStorageInterface objectStreamStorage) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(objectStreamStorage, "objectStreamStorage must not be ull");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.objectStreamStorage = objectStreamStorage;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doRemove);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        int size;
        try {
            size = (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Error determining the size of directory ", null, e);
        }
        return size;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + File.separator + uuid);
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file, NOFOLLOW_LINKS);
    }

    @Override
    protected void doSave(Resume resume, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file" + file.getFileName().toString(), file.getFileName().toString(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getFileName().toString());
        }
    }

    @Override
    protected void doRemove(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path delete error", file.getFileName().toString());
        }
    }

    @Override
    protected Resume[] doGetAll() {
        List<Path> listPaths;
        try {
            listPaths = Files
                    .list(directory)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Error while getting all paths", null, e);
        }

        Resume[] resumes = new Resume[listPaths.size()];
        for (int i = 0; i < listPaths.size(); i++) {
            resumes[i] = doGet(listPaths.get(i));
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
