package com.javaops.webapp.model;

import java.util.List;

public class ListSection implements Section<List<String>, String> {
    private List<String> sectionStorage;

    public ListSection(List<String> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    @Override
    public List<String> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(String content) {
        this.sectionStorage.add(content);
    }
}
