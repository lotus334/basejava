package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection implements Section<List<Experience>> {
    private List<Experience> sectionStorage;

    public ExperienceSection(List<Experience> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    @Override
    public List<Experience> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(List<Experience> content) {
        sectionStorage.addAll(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return sectionStorage.equals(that.sectionStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionStorage);
    }
}
