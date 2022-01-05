package com.javaops.webapp.model;

import java.util.List;

public class ExperienceSection implements Section<List<Experience>, Experience> {
    private List<Experience> sectionStorage;

    public ExperienceSection(List<Experience> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    @Override
    public List<Experience> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(Experience content) {
        sectionStorage.add(content);
    }
}
