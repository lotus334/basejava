package com.javaops.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapByUuidStorageTest.class,
                MapByResumeStorageTest.class
        }
)
public class AllStorageTest {
}
