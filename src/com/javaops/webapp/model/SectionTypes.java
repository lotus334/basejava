package com.javaops.webapp.model;

public enum SectionTypes {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
