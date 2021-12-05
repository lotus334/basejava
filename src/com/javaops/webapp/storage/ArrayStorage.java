package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int lastIndex = 0;

    public void clear() {
        for (int i = 0; i < lastIndex; i++) {
            storage[i] = null;
        }
        lastIndex = 0;
    }

    public void save(Resume r) {
        storage[lastIndex] = r;
        lastIndex++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i])) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i])) {
                storage[i] = storage[lastIndex - 1];
                storage[lastIndex - 1] = null;
                lastIndex--;
                return;
            }
        }
    }

    public void update(Resume resume) {
        for (int i = 0; i < lastIndex; i++) {
            if (resume.equals(storage[i])) {
                storage[i] = resume;
                System.out.println("successfully update");
                return;
            }
        }
        System.out.println("object not found");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, lastIndex);
    }

    public int size() {
        return lastIndex;
    }
}
