package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<Organization> sectionStorage;

    public OrganizationSection() {}

    public OrganizationSection(List<Organization> sectionStorage) {
        Objects.requireNonNull(sectionStorage, "sectionStorage must not be null");
        this.sectionStorage = sectionStorage;
    }

    public List<Organization> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return sectionStorage.equals(that.sectionStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionStorage);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "sectionStorage=" + sectionStorage +
                '}';
    }
}
