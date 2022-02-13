package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;
import java.nio.file.*;
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
        return directory.getNameCount();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        List<Path> list = null;
        try {
            list = Files
                    .list(directory)
                    .filter(el -> el.getFileName().toString().equals(uuid))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

//    @Override
//    protected void doUpdate(Path file, Resume resume) {
//        try {
//            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
//        } catch (IOException e) {
//            throw new StorageException("File write error", file.getName(), e);
//        }
//    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file, NOFOLLOW_LINKS);
    }

//    @Override
//    protected void doSave(Resume resume, Path file) {
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            throw new StorageException("Couldn't create file" + file.getAbsolutePath(), file.getName(), e);
//        }
//        doUpdate(file, resume);
//    }

//    @Override
//    protected Resume doGet(File file) {
//        try {
//            return doRead(new BufferedInputStream(new FileInputStream(file)));
//        } catch (IOException e) {
//            throw new StorageException("File read error", file.getName());
//        }
//    }

    @Override
    protected void doRemove(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path delete error", file.toString());
        }
    }

//    @Override
//    protected Resume[] doGetAll() {
//        File[] listFiles = directory.listFiles();
//        if (listFiles == null) {
//            throw new StorageException("Directory read error", null);
//        }
//        Resume[] resumes = new Resume[listFiles.length];
//        for (int i = 0; i < listFiles.length; i++) {
//            resumes[i] = doGet(listFiles[i]);
//        }
//        return resumes;
//    }

    protected abstract Resume doRead(InputStream inputStream) throws IOException;

    protected abstract void doWrite(Resume r, OutputStream outputStream) throws IOException;
}
