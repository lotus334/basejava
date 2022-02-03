package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection implements Section {

    private List<Organization> sectionStorage;

    public OrganizationSection(List<Organization> sectionStorage) {
        Objects.requireNonNull(sectionStorage, "sectionStorage must not be null");
        this.sectionStorage = sectionStorage;
    }

    public List<Organization> getSectionStorage() {
        return sectionStorage;
    }

    public void printContent() {
        for (Organization exp : sectionStorage) {
            System.out.println(exp.getTitle());
            System.out.println(exp.getLink());
            for (Position position : exp.getPositions()) {
                System.out.println(position.getDateFrom());
                System.out.println(position.getDateTo());
                System.out.println(position.getDescription());
                if (position.getAdditionalInfo() != null) {
                    System.out.println(position.getAdditionalInfo());
                }
            }
        }
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
