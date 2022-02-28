package com.javaops.webapp.storage.serializer;

import com.javaops.webapp.model.Resume;
import com.javaops.webapp.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializer {
    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }
}
