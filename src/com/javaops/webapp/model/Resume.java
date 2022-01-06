package com.javaops.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;
    private Map<SectionTypes, Section> sections = new TreeMap<>();

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString());
    }

    public Resume(String fullName, String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(uuid, "fullName must not be null");
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.fullName = fullName;
    }

    public <T> void setSection(SectionTypes section, T content) {
        Objects.requireNonNull(content, "can not set content as null");
        switch (section) {
            case CONTACTS -> {
                sections.put(section, new ContactSection((Map<ContactTypes, String>) content));
                break;
            }
            case PERSONAL, OBJECTIVE -> {
                sections.put(section, new TextSection((String) content));
                break;
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                sections.put(section, new ListSection((List<String>) content));
                break;
            }
            case EDUCATION, EXPERIENCE -> {
                sections.put(section, new ExperienceSection((List<Experience>) content));
                break;
            }
        }
    }

    public Set<SectionTypes> getSections() {
        return sections.keySet();
    }

    public Section getSection(SectionTypes section) {
        if (sections.get(section) != null) {
            return sections.get(section);
        }
        return null;
    }

    public <T> T getSectionStorage(SectionTypes section) {
        if (sections.get(section) != null) {
            return (T) sections.get(section).getSectionStorage();
        }
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections);
    }
}