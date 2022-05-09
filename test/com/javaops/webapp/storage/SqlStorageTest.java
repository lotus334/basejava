package com.javaops.webapp.storage;

import com.javaops.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    static final String DB_URL = Config.get().getProps().getProperty("db.url");
    static final String DB_USER = Config.get().getProps().getProperty("db.user");
    static final String DB_PASSWORD = Config.get().getProps().getProperty("db.password");

    public SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}
