package com.javaops.webapp.model;

import java.util.HashMap;
import java.util.Map;

public class ContactSection implements Section {
    private Map<ContactTypes, String> contacts = new HashMap<>();

    @Override
    public <T> T getContent() {
        return null;
    }
}
