package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;
import org.junit.Before;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Before
    public void setUpSortedArrayStorage() {
        expectedResumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
    }
}