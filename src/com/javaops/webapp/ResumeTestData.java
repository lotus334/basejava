package com.javaops.webapp;

import com.javaops.webapp.model.Resume;
import com.javaops.webapp.model.SectionTypes;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume myResume = new Resume("DmitriyVass");
        myResume.setSection(SectionTypes.OBJECTIVE, "Developer");

        for (SectionTypes section : SectionTypes.values()) {
            System.out.println((String) myResume.getSection(section));
        }
    }
}
