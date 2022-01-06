package com.javaops.webapp.model;

public class TextSection implements Section<String> {
    private String sectionStorage;

    public TextSection(String content) {
        this.sectionStorage = content;
    }

    @Override
    public String getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(String content) {
        this.sectionStorage = content;
    }

    @Override
    public String toString() {
        return sectionStorage;
    }
}
