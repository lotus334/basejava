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
            dos.writeInt(contacts.size());

            writeWithException(contacts.entrySet(), dos, contactTypesStringEntry -> {
                dos.writeUTF(contactTypesStringEntry.getKey().name());
                dos.writeUTF(contactTypesStringEntry.getValue());
            });

            dos.writeInt(resume.getSections().size());

            Set<SectionTypes> sectionTypes = Set.of(SectionTypes.values());

            writeWithException(sectionTypes, dos, sectionType -> {
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
                            dos.writeInt(skills.size());
                            for (String skill : skills) {
                                dos.writeUTF(skill);
                            }
                            break;

                        case EDUCATION:
                        case EXPERIENCE:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> organizations = organizationSection.getSectionStorage();

                            dos.writeInt(organizations.size());

                            writeWithException(organizations, dos, organization -> {
                                Link homePage = organization.getHomePage();
                                dos.writeUTF(homePage.getName());

                                boolean isUrlEmpty = homePage.getUrl() == null;
                                dos.writeBoolean(isUrlEmpty);
                                if (!isUrlEmpty) {
                                    dos.writeUTF(homePage.getUrl());
                                }

                                List<Position> positions = organization.getPositions();

                                dos.writeInt(positions.size());

                                writeWithException(positions, dos, position -> {
                                    dos.writeInt(position.getDateFrom().getYear());
                                    dos.writeInt(position.getDateFrom().getMonthValue());
                                    dos.writeInt(position.getDateTo().getYear());
                                    dos.writeInt(position.getDateTo().getMonthValue());
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
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(fullName, uuid);

            int contactsSize = dis.readInt();
            Map<ContactTypes, String> contacts = new EnumMap<>(ContactTypes.class);
            for (int i = 0; i < contactsSize; i++) {
                contacts.put(ContactTypes.valueOf(dis.readUTF()), dis.readUTF());
            }
            resume.setContacts(contacts);

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionTypes sectionType = SectionTypes.valueOf(dis.readUTF());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = new TextSection(dis.readUTF());

                        chooseAndSetSection(resume, sectionType, PERSONAL, SectionTypes.OBJECTIVE, textSection);
                        break;

                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        int listSize = dis.readInt();
                        List<String> skills = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            skills.add(dis.readUTF());
                        }
                        ListSection listSection = new ListSection(skills);

                        chooseAndSetSection(resume, sectionType, SectionTypes.ACHIEVEMENT, SectionTypes.QUALIFICATIONS, listSection);
                        break;

                    case EDUCATION:
                    case EXPERIENCE:
                        int organizationsSize = dis.readInt();

                        List<Organization> organizations = new ArrayList<>();

                        for (int j = 0; j < organizationsSize; j++) {
                            String homePageName = dis.readUTF();
                            boolean isUlrEmpty = dis.readBoolean();
                            Link homePage;

                            if (!isUlrEmpty) {
                                String homePageUrl = dis.readUTF();
                                homePage = new Link(homePageName, homePageUrl);
                            } else {
                                homePage = new Link(homePageName, null);
                            }

                            List<Position> positions = new ArrayList<>();

                            int positionsSize = dis.readInt();

                            for (int k = 0; k < positionsSize; k++) {
                                int yearFrom = dis.readInt();
                                int monthFrom = dis.readInt();
                                YearMonth dateFrom = YearMonth.of(yearFrom, monthFrom);
                                int yearTo = dis.readInt();
                                int monthTo = dis.readInt();
                                YearMonth dateTo = YearMonth.of(yearTo, monthTo);
                                String description = dis.readUTF();
                                String additionalInfo = null;
                                boolean isAdditInfoEmpty = dis.readBoolean();
                                if (!isAdditInfoEmpty) {
                                    additionalInfo = dis.readUTF();
                                }
                                Position position = new Position(dateFrom, dateTo, description, additionalInfo);
                                positions.add(position);
                            }

                            Organization organization = new Organization(homePage, positions);
                            organizations.add(organization);
                        }
                        OrganizationSection organizationSection = new OrganizationSection(organizations);

                        chooseAndSetSection(resume, sectionType, SectionTypes.EDUCATION, SectionTypes.EXPERIENCE, organizationSection);
                        break;
                }
            }
            return resume;
        }
    }

    private void chooseAndSetSection(Resume resume, SectionTypes sectionType, SectionTypes sectionType1, SectionTypes sectionType2, Section section) {
        if (sectionType == sectionType1) {
            resume.setSection(sectionType1, section);
        } else {
            resume.setSection(sectionType2, section);
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<T> customConsumer) throws IOException {
        for (T t : collection) {
            customConsumer.accept(t);
        }
    }
}