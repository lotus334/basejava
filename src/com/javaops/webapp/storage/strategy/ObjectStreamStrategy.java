package com.javaops.webapp.storage.strategy;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStrategy implements Strategy {

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }
}
