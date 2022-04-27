package com.javaops.webapp.storage.serializer;

import com.javaops.webapp.model.*;
import com.javaops.webapp.storage.AbstractStorage;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

                if (section == null) {
                    continue;
                } else if (section.getClass().equals(TextSection.class)) {
                    dos.writeUTF(sectionType.getTitle());
                    dos.writeUTF(((TextSection) section).getArticle());
                } else if (section.getClass().equals(ListSection.class)) {
                    dos.writeUTF(sectionType.getTitle());
                    List<String> skills = ((ListSection) section).getSkills();
                    dos.writeInt(skills.size());
                    for (String skill : skills) {
                        dos.writeUTF(skill);
                    }
                } else if (section.getClass().equals(OrganizationSection.class)) {
                    dos.writeUTF(sectionType.getTitle());

                    OrganizationSection organizationSection = (OrganizationSection) section;
                    List<Organization> organizations = organizationSection.getSectionStorage();

                    dos.writeInt(organizations.size());

                    for (Organization organization : organizations) {
                        Link homePage = organization.getHomePage();
                        dos.writeUTF(homePage.getName());

                        boolean isUrlEmpty = homePage.getUrl() == null;
                        dos.writeBoolean(isUrlEmpty);
                        if (!isUrlEmpty) {
                            dos.writeUTF(homePage.getUrl());
                        }

                        List<Position> positions = organization.getPositions();

                        dos.writeInt(positions.size());

                        for (Position position : positions) {
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
                        }
                    }
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

            int contactsSize = dis.readInt();
            Map<ContactTypes, String> contacts = new EnumMap<>(ContactTypes.class);
            for (int i = 0; i < contactsSize; i++) {
                contacts.put(ContactTypes.valueOf(dis.readUTF()), dis.readUTF());
            }
            resume.setContacts(contacts);

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                String sectionType = dis.readUTF();
                if (isTextSection(sectionType)) {
                    TextSection textSection = new TextSection(dis.readUTF());

                    if (sectionType.equals(SectionTypes.PERSONAL.getTitle())) {
                        resume.setSection(SectionTypes.PERSONAL, textSection);
                    } else {
                        resume.setSection(SectionTypes.OBJECTIVE, textSection);
                    }
                } else if (isListSection(sectionType)) {
                    int listSize = dis.readInt();
                    List<String> skills = new ArrayList<>();
                    for (int j = 0; j < listSize; j++) {
                        skills.add(dis.readUTF());
                    }
                    ListSection listSection = new ListSection(skills);

                    if (sectionType.equals(SectionTypes.ACHIEVEMENT.getTitle())) {
                        resume.setSection(SectionTypes.ACHIEVEMENT, listSection);
                    } else {
                        resume.setSection(SectionTypes.QUALIFICATIONS, listSection);
                    }
                } else if (isOrganizationSection(sectionType)) {
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

                    if (sectionType.equals(SectionTypes.EDUCATION.getTitle())) {
                        resume.setSection(SectionTypes.EDUCATION, organizationSection);
                    } else {
                        resume.setSection(SectionTypes.EXPERIENCE, organizationSection);
                    }
                }
            }

            // TODO implements sections
            return resume;
        }
    }

    private boolean isTextSection(String sectionType) {
        return sectionType.equals(SectionTypes.PERSONAL.getTitle()) || sectionType.equals(SectionTypes.OBJECTIVE.getTitle());
    }

    private boolean isListSection(String type) {
        return type.equals(SectionTypes.ACHIEVEMENT.getTitle()) || type.equals(SectionTypes.QUALIFICATIONS.getTitle());
    }

    private boolean isOrganizationSection(String type) {
        return type.equals(SectionTypes.EDUCATION.getTitle()) || type.equals(SectionTypes.EXPERIENCE.getTitle());
    }
}
