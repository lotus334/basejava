package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage(), new Resume[]{RESUME_1, RESUME_3, RESUME_2});
    }
}