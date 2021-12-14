package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;
import org.junit.Before;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Before
    public void setUpArrayStorage() {
        expectedResumes = new Resume[]{RESUME_1, RESUME_3, RESUME_2};
    }
}