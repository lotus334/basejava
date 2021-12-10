package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    ArrayStorage arrayStorage;

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }

    @Before
    public void setUp() throws Exception {
        setUpAbstractArrayStorage();
        arrayStorage = (ArrayStorage) storage;
    }

    @Test
    public void insert() {
        Resume resume = new Resume("uuid4");
        arrayStorage.insert(resume, arrayStorage.size());
        arrayStorage.size++;
        Assert.assertEquals(arrayStorage.getAll()[3], resume);
    }

    @Test
    public void remove() {
        int index = arrayStorage.getIndex("uuid3");
        arrayStorage.remove(index);
        Resume latest = arrayStorage.getAll()[arrayStorage.size - 1];
        Resume indexed = arrayStorage.getAll()[index];
        Assert.assertEquals(indexed, latest);
    }

    @Test
    public void getExistingIndex() {
        int index = arrayStorage.getIndex("uuid3");
        Assert.assertEquals(index, 2);
    }

    @Test
    public void getNonExistingIndex() {
        int index = arrayStorage.getIndex("uuid4");
        Assert.assertEquals(index, -1);
    }
}