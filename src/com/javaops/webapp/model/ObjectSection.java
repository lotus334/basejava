package com.javaops.webapp.model;

public class ObjectSection implements Section {
    String title;
    String dateFrom;
    String dateTo;
    String description;
    String additionalInfo;

    @Override
    public <T> T getContent() {
        return null;
    }
}
