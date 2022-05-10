package com.javaops.webapp;

import com.javaops.webapp.storage.SqlStorage;
import com.javaops.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Storage storage;
    private final File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
        storage = new SqlStorage(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
                );
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
