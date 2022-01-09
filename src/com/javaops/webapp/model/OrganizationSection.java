package com.javaops.webapp.model;

import java.util.List;

public class OrganizationSection implements Section {

    private List<Organization> sectionStorage;

    public OrganizationSection(List<Organization> sectionStorage) {
        this.sectionStorage = sectionStorage;
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
}
