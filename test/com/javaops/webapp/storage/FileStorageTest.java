package com.javaops.webapp.storage;

import com.javaops.webapp.storage.strategy.ObjectStreamStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}