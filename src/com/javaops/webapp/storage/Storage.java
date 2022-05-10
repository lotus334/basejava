package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);

    void clear();

    int size();

    List<Resume> getAllSorted();

    Resume get(String uuid);
}