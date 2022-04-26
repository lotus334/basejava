package com.javaops.webapp.storage.serializer;

import com.javaops.webapp.model.*;

import java.io.*;
import java.util.EnumMap;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactTypes, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactTypes, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            dos.writeInt(resume.getSections().size());
            for (SectionTypes sectionType : SectionTypes.values()) {
                Section section = resume.getSection(sectionType);

                if (section != null && section.getClass().equals(TextSection.class)) {
                    dos.writeUTF(sectionType.getTitle());
                    dos.writeUTF(((TextSection) section).getArticle());
                }
            }
            // TODO implements sections
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(fullName, uuid);

            int size = dis.readInt();
            Map<ContactTypes, String> contacts = new EnumMap<>(ContactTypes.class);
            for (int i = 0; i < size; i++) {
                contacts.put(ContactTypes.valueOf(dis.readUTF()), dis.readUTF());
            }
            resume.setContacts(contacts);

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String type = dis.readUTF();
                if (type.equals(SectionTypes.OBJECTIVE.getTitle())) {
                    TextSection section = new TextSection(dis.readUTF());

                    resume.setSection(SectionTypes.OBJECTIVE, section);
                }
            }

            // TODO implements sections
            return resume;
        }
    }
}
