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
        Arrays.fill(storage, 0, lastIndex, null);
        lastIndex = 0;
        System.out.println("Successfully clear storage");
    }

    public void save(Resume resume) {
        if (lastIndex == MAX_SIZE) {
            System.out.println("Failed to save: storage is full");
            return;
        }
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Failed to save: resume " + resume.toString() + " already exist");
            return;
        }
        storage[lastIndex] = resume;
        lastIndex++;
        System.out.println("Successfully save: resume " + resume.toString());
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            return storage[resumeIndex];
        }
        return null;
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex != -1) {
            storage[resumeIndex] = storage[lastIndex - 1];
            storage[lastIndex - 1] = null;
            lastIndex--;
            System.out.println("Successfully delete: resume " + uuid);
            return;
        }
        System.out.println("Failed to delete: resume " + uuid + " not found");
    }

    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex != -1) {
            storage[resumeIndex] = resume;
            System.out.println("Successfully update resume " + resume.toString());
            return;
        }
        System.out.println("Failed to update: resume " + resume.toString() + " not found");
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

    private int getIndex(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
