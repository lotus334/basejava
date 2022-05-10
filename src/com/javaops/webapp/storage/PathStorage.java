package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private StreamSerializer objectStreamStorage;

    protected PathStorage(String directory, StreamSerializer objectStreamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(objectStreamStorage, "objectStreamStorage must not be ull");
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
        Objects.requireNonNull(objectStreamStorage, "objectStreamStorage must not be null");
        this.objectStreamStorage = objectStreamStorage;
    }

    @Override
    public void doClear() {
        getFiles().forEach(this::doRemove);
    }

    @Override
    public int getSize() {
        return (int) getFiles().count();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            objectStreamStorage.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", getFileName(path), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path" + path, getFileName(path), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return objectStreamStorage.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doRemove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    protected Resume[] doGetAll() {
        return getFiles()
                .map(this::doGet)
                .toArray(Resume[]::new);
    }

    private Stream<Path> getFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e.getMessage(), e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
