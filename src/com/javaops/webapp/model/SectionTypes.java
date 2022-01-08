package com.javaops.webapp.model;

public enum SectionTypes {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    public String title;

    SectionTypes(String title) {
        this.title = title;
    }
}
