package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public interface Storage {

    Comparator<Resume> RESUME_COMPARATOR = ((Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid)));
    Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    void save(Resume resume);

    void delete(String uuid);

    void update(Resume resume);

    void clear();

    int size();

    List<Resume> getAllSorted();

    Resume get(String uuid);
}