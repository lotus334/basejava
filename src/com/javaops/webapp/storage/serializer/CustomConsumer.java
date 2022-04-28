package com.javaops.webapp.storage.serializer;

import java.io.DataOutputStream;
import java.io.IOException;

public interface CustomConsumer<T> {
    void accept(T t) throws IOException;
}
