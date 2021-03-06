package com.javaops.webapp.storage;

import com.javaops.webapp.Config;
import com.javaops.webapp.ResumeTestData;
import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.ContactTypes;
import com.javaops.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final Comparator<Resume> RESUME_COMPARATOR = ((Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid)));

    protected static final String UUID_1 = UUID.randomUUID().toString();
    protected static final String UUID_2 = UUID.randomUUID().toString();
    protected static final String UUID_3 = UUID.randomUUID().toString();
    protected static final String UUID_4 = UUID.randomUUID().toString();
    protected static final Resume RESUME_1 = ResumeTestData.createFilledResume("fullNameC", UUID_1);
    protected static final Resume RESUME_2 = ResumeTestData.createFilledResume("fullNameB", UUID_2);
    protected static final Resume RESUME_3 = ResumeTestData.createFilledResume("fullNameA", UUID_3);
    protected static final Resume RESUME_4 = ResumeTestData.createFilledResume("fullNameD", UUID_4);

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_3);
        storage.save(RESUME_2);
        RESUME_4.setContact(ContactTypes.STACKOVERFLOW, "overflow");
        RESUME_1.setContact(ContactTypes.HOMEPAGE, "homepage");
        RESUME_3.setContact(ContactTypes.GITHUB, "gh");
        RESUME_2.setContact(ContactTypes.GITHUB, "git");
    }

    @Test
    public void save() {
        int sizeBefore = storage.size();
        storage.save(RESUME_4);
        assertEquals(sizeBefore + 1, storage.size());
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int sizeBefore = storage.size();
        storage.delete(UUID_3);
        assertEquals(sizeBefore - 1, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void update() {
        Resume newResume = new Resume("fullName3", UUID_3);
        RESUME_3.setContact(ContactTypes.STACKOVERFLOW, "overflow");
        RESUME_3.setContact(ContactTypes.HOMEPAGE, "homepage");
        RESUME_3.setContact(ContactTypes.GITHUB, "git");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = List.of(RESUME_3, RESUME_2, RESUME_1)
                .stream()
                .sorted(RESUME_COMPARATOR)
                .collect(Collectors.toList());
        assertEquals(expectedResumes, storage.getAllSorted());
    }

    protected void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}