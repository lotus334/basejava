package com.javaops.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ContactSection implements Section {
    private List<Contact> contacts = new ArrayList<>();

    @Override
    public <T> T getContent() {
        return null;
    }
}
