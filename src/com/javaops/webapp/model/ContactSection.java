package com.javaops.webapp.model;

import java.util.Map;
import java.util.Objects;

public class ContactSection implements Section<Map<ContactTypes, String>> {
    private Map<ContactTypes, String> sectionStorage;

    ContactSection(Map<ContactTypes, String> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    @Override
    public Map<ContactTypes, String> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(Map<ContactTypes, String> content) {
        sectionStorage.putAll(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactSection that = (ContactSection) o;
        return sectionStorage.equals(that.sectionStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionStorage);
    }
}
