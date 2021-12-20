package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_1 = new Resume("fullName1", UUID_1);
    protected static final Resume RESUME_2 = new Resume("fullName2", UUID_2);
    protected static final Resume RESUME_3 = new Resume("fullName3", UUID_3);
    protected static final Resume RESUME_4 = new Resume("fullName4", UUID_4);

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_2);
    }

    @Test
    public void save() throws Exception {
        int sizeBefore = storage.size();
        storage.save(RESUME_4);
        assertEquals(sizeBefore + 1, storage.size());
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        int sizeBefore = storage.size();
        storage.delete(UUID_3);
        assertEquals(sizeBefore - 1, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    // TO DO when in Resume appears new fields
    @Test
    public void update() throws Exception {
        Resume newResume = new Resume("fullName3", UUID_3);
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> expectedResumes = List.of(RESUME_1, RESUME_2, RESUME_3);
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    protected void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}