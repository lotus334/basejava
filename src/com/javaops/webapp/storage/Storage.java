package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public interface Storage {

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);

    void clear();

    int size();

    Resume[] getAll();

    Resume get(String uuid);
}