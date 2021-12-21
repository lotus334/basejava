package com.javaops.webapp.model;

public enum Sections {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    public String title;

    Sections(String title) {
        this.title = title;
    }
}
