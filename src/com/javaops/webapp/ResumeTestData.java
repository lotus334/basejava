package com.javaops.webapp;

import com.javaops.webapp.model.Resume;
import com.javaops.webapp.model.Sections;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume myResume = new Resume("DmitriyVass");
        myResume.setSection(Sections.OBJECTIVE, "Developer");

        for (Sections section : Sections.values()) {
            System.out.println((String) myResume.getSection(section));
        }
    }
}
