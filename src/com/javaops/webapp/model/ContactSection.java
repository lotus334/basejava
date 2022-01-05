package com.javaops.webapp.model;

import java.util.Map;

public class ContactSection implements Section {
    private Map<ContactTypes, String> contacts;

    ContactSection(Map<ContactTypes, String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public Map<ContactTypes, String> getContent() {
        return contacts;
    }
}
