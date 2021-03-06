package com.javaops.webapp;

import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.MapByResumeStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for com.javaops.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static MapByResumeStorage ARRAY_STORAGE = new MapByResumeStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    resume = new Resume(uuid);
                    try {
                        ARRAY_STORAGE.save(resume);
                    } catch (StorageException e) {
                        System.out.println(e.getMessage());
                    }
                    printAll();
                    break;
                case "delete":
                    try {
                        ARRAY_STORAGE.delete(uuid);
                    } catch (NotExistStorageException e) {
                        System.out.println(e.getMessage());
                    }
                    printAll();
                    break;
                case "get":
                    try {
                        System.out.println(ARRAY_STORAGE.get(uuid));
                    } catch (NotExistStorageException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "update":
                    resume = new Resume(uuid);
                    ARRAY_STORAGE.update(resume);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
