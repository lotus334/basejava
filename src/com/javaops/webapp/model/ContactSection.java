package com.javaops.webapp.model;

import java.util.Map;

public class ContactSection implements Section<Map<ContactTypes, String>, Map<ContactTypes, String>> {
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
}
