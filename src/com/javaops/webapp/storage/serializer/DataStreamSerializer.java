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
                readSection(resume, dis);
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

//    должно быть 2а метода
//    Метод который читает и заполняет наше резюме.
//    Метод который читает и отдает коллекцию List так же типизированную, нужно будет использовать их для чтения SectionType.EXPERIENCE и SectionType.EDUCATION
    private <T> void readWithException(DataInputStream dis, VendorWithException consumer) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            consumer.get();
        }
    }

    private void writePositionDate(YearMonth date, DataOutputStream dos) throws IOException{
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
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

    private void readSection(Resume resume, DataInputStream dis) throws IOException {
        SectionTypes sectionType = valueOf(dis.readUTF());

        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                resume.setSection(sectionType, new TextSection(dis.readUTF()));
                break;

            case QUALIFICATIONS:
            case ACHIEVEMENT:
                List<String> skills = new ArrayList<>();
                readWithException(dis, () -> skills.add(dis.readUTF()));
                resume.setSection(sectionType, new ListSection(skills));
                break;

            case EDUCATION:
            case EXPERIENCE:
                List<Organization> organizations = new ArrayList<>();

                readWithException(dis, () -> {
                    String homePageName = dis.readUTF();
                    Link homePage = new Link(homePageName, dis.readBoolean() ? null : dis.readUTF());

                    List<Position> positions = new ArrayList<>();

                    readWithException(dis, () -> {
                        YearMonth dateFrom = YearMonth.of(dis.readInt(), dis.readInt());
                        YearMonth dateTo = YearMonth.of(dis.readInt(), dis.readInt());
                        String description = dis.readUTF();
                        String additionalInfo = dis.readBoolean() ? null : dis.readUTF();
                        Position position = new Position(dateFrom, dateTo, description, additionalInfo);
                        positions.add(position);
                    });

                    Organization organization = new Organization(homePage, positions);
                    organizations.add(organization);
                });
                resume.setSection(sectionType, new OrganizationSection(organizations));
                break;
        }
    }
}

interface ConsumerWithException<T> {
    void accept(T t) throws IOException;
}

interface VendorWithException {
    void get() throws IOException;
}