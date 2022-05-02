package com.javaops.webapp.storage.serializer;

import com.javaops.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.*;

import static com.javaops.webapp.model.SectionTypes.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactTypes, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, contactTypesStringEntry -> {
                dos.writeUTF(contactTypesStringEntry.getKey().name());
                dos.writeUTF(contactTypesStringEntry.getValue());
            });

            Set<SectionTypes> sectionTypes = resume.getSections().keySet();
            writeWithException(sectionTypes, dos, sectionType -> writeSection(resume, sectionType, dos));
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(fullName, uuid);

            Map<ContactTypes, String> contacts = new EnumMap<>(ContactTypes.class);
            readWithException(dis, () -> contacts.put(ContactTypes.valueOf(dis.readUTF()), dis.readUTF()));
            resume.setContacts(contacts);

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionTypes sectionType = valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(sectionType, dis));
            }
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, ConsumerWithException<T> consumerWithException) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumerWithException.accept(t);
        }
    }

    private <T> void readWithException(DataInputStream dis, VendorWithException vendor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            vendor.take();
        }
    }

    private void writeSection(Resume resume, SectionTypes sectionType, DataOutputStream dos) throws IOException {
        Section section = resume.getSection(sectionType);

        if (section != null) {
            dos.writeUTF(String.valueOf(sectionType));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    dos.writeUTF(((TextSection) section).getArticle());
                    break;

                case QUALIFICATIONS:
                case ACHIEVEMENT:
                    List<String> skills = ((ListSection) section).getSkills();
                    writeWithException(skills, dos, dos::writeUTF);
                    break;

                case EDUCATION:
                case EXPERIENCE:
                    OrganizationSection organizationSection = (OrganizationSection) section;
                    List<Organization> organizations = organizationSection.getSectionStorage();

                    writeWithException(organizations, dos, organization -> {
                        Link homePage = organization.getHomePage();
                        dos.writeUTF(homePage.getName());

                        boolean isUrlEmpty = homePage.getUrl() == null;
                        dos.writeBoolean(isUrlEmpty);
                        if (!isUrlEmpty) {
                            dos.writeUTF(homePage.getUrl());
                        }

                        List<Position> positions = organization.getPositions();

                        writeWithException(positions, dos, position -> {
                            writePositionDate(position.getDateFrom(), dos);
                            writePositionDate(position.getDateTo(), dos);
                            dos.writeUTF(position.getDescription());

                            boolean isAdditInfoEmpty = position.getAdditionalInfo() == null;
                            dos.writeBoolean(isAdditInfoEmpty);
                            if (!isAdditInfoEmpty) {
                                dos.writeUTF(position.getAdditionalInfo());
                            }
                        });
                    });
                    break;
            }
        }
    }

    private Section readSection(SectionTypes sectionType, DataInputStream dis) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());

            case QUALIFICATIONS:
            case ACHIEVEMENT:
                List<String> skills = new ArrayList<>();
                readWithException(dis, () -> skills.add(dis.readUTF()));
                return new ListSection(skills);

            case EDUCATION:
            case EXPERIENCE:
                return new OrganizationSection(
                        readList(dis, () -> new Organization(
                                new Link(dis.readUTF(), dis.readBoolean() ? null : dis.readUTF()),
                                readList(dis, () -> new Position(
                                        readPositionDate(dis),
                                        readPositionDate(dis),
                                        dis.readUTF(),
                                        dis.readBoolean() ? null : dis.readUTF())
                                ))
                        ));
            default:
                throw new IllegalStateException();
        }
    }

    private void writePositionDate(YearMonth date, DataOutputStream dos) throws IOException{
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private YearMonth readPositionDate(DataInputStream dis) throws IOException {
        return YearMonth.of(dis.readInt(), dis.readInt());
    }

    private <T> List<T> readList(DataInputStream dis, SupplierWithException<T> vendor) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(vendor.get());
        }
        return list;
    }

    private interface ConsumerWithException<T> {
        void accept(T t) throws IOException;
    }

    private interface VendorWithException {
        void take() throws IOException;
    }

    private interface SupplierWithException<T> {
        T get() throws IOException;
    }
}