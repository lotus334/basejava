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

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
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
        return directory.toFile().list().length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + "/" + uuid);
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("File write error", file.toFile().getName(), e);
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
            throw new StorageException("Couldn't create file" + file.toFile().getAbsolutePath(), file.toFile().getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("File read error", file.toFile().getName());
        }
    }

    @Override
    protected void doRemove(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path delete error", file.toString());
        }
    }

    @Override
    protected Resume[] doGetAll() {
        List<Path> listPaths = null;
        try {
            listPaths = Files
                    .list(directory)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Resume[] resumes = new Resume[listPaths.size()];
        for (int i = 0; i < listPaths.size(); i++) {
            resumes[i] = doGet(listPaths.get(i));
        }
        return resumes;
    }

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume r, OutputStream outputStream) throws IOException;
}
