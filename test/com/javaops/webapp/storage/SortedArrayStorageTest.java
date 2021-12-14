package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage(), new Resume[]{RESUME_1, RESUME_2, RESUME_3});
    }
}