package com.javaops.webapp.model;

import java.util.List;

public class ExperienceSection implements Section {

    private List<Experience> sectionStorage;

    public ExperienceSection(List<Experience> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    public void printContent() {
        for (Experience exp : sectionStorage) {
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
