package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    SortedArrayStorage sortedArrayStorage;

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Before
    public void setUp() throws Exception {
        setUpAbstractArrayStorage();
        sortedArrayStorage = (SortedArrayStorage) storage;
    }

    @Test
    public void insert() {
        Resume resume = new Resume("uuid0");
        sortedArrayStorage.insert(resume, sortedArrayStorage.getIndex(resume.getUuid()));
        sortedArrayStorage.size++;
        Assert.assertEquals(sortedArrayStorage.getAll()[0], resume);
    }

    @Test
    public void remove() {
        int index = sortedArrayStorage.getIndex("uuid1");
        sortedArrayStorage.remove(index);
        Assert.assertEquals(sortedArrayStorage.getIndex("uuid1"), -1);
    }

    @Test
    public void getIndex() {
        int index = sortedArrayStorage.getIndex("uuid1");
        Assert.assertEquals(index, 0);
    }

    @Test
    public void getNonIndex() {
        int index = sortedArrayStorage.getIndex("uuid0");
        Assert.assertEquals(index, -1);
    }
}