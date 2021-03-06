package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("dummy"));
            }
        } catch (StorageException e) {
            fail("the overflow happened early");
        }
        storage.save(RESUME_4);
    }
}