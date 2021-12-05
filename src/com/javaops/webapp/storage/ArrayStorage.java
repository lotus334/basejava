package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int MAX_SIZE = 10_000;
    private Resume[] storage = new Resume[MAX_SIZE];
    private int lastIndex = 0;

    public void clear() {
        Arrays.fill(storage, 0 , lastIndex, null);
        lastIndex = 0;
    }

    public void save(Resume r) {
        if (lastIndex == MAX_SIZE) {
            System.out.println("Failed to save: storage is full");
            return;
        }
        if (get(r.getUuid(), false) != null) {
            System.out.println("Failed to save: object already exist.");
            return;
        }
        storage[lastIndex] = r;
        lastIndex++;
    }

    public Resume get(String uuid, boolean printMessage) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        if (printMessage) {
            System.out.println("Failed to get: object not found.");
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = storage[lastIndex - 1];
                storage[lastIndex - 1] = null;
                lastIndex--;
                return;
            }
        }
        System.out.println("Failed to delete: object not found.");
    }

    public void update(Resume resume) {
        for (int i = 0; i < lastIndex; i++) {
            if (resume.equals(storage[i])) {
                storage[i] = resume;
                System.out.println("successfully update");
                return;
            }
        }
        System.out.println("Failed to update: object not found.");
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
