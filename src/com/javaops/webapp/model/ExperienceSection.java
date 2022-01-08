package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection implements Section {
    @Override
    public String toString() {
        return "ExperienceSection{" +
                "sectionStorage=" + sectionStorage +
                '}';
    }

    private List<Experience> sectionStorage;

    public ExperienceSection(List<Experience> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    public void printContent() {
        for (Experience exp : sectionStorage) {
            System.out.println(exp.toString());
        }
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
