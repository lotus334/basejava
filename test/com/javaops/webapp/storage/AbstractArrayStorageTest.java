package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUpAbstractArrayStorage() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    // TO DO when in Resume appears new fields
    @Test
    public void update() throws Exception {
        Resume resume = new Resume("uuid3");
//        storage.update(resume);
        Assert.assertEquals(storage.getAll()[2], resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.update(resume);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = new Resume[storage.size()];
        expected[0] = storage.get("uuid1");
        expected[1] = storage.get("uuid2");
        expected[2] = storage.get("uuid3");
        Assert.assertArrayEquals(storage.getAll(), expected);
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(storage.getAll()[3], resume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        Resume resume = new Resume("uuid3");
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("the overflow happened early");
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() throws Exception {
        Resume resume3 = storage.get("uuid3");
        storage.delete("uuid3");
        Resume resume1 = storage.getAll()[0];
        Resume resume2 = storage.getAll()[1];
        Assert.assertTrue(storage.size() == 2 && resume1 != resume3 && resume2 != resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        Resume resume = storage.get("uuid3");
        Resume expectedResume = storage.getAll()[2];
        Assert.assertEquals(resume, expectedResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}