package com.javaops.webapp.model;

public enum ContactTypes {

    TELEPHONE("Контакты"),
    SKYPE("Личные качества"),
    EMAIL("Позиция"),
    LINKEDIN("Достижения"),
    GITHUB("Квалификация"),
    STACKOVERFLOW("Опыт работы"),
    HOMEPAGE("Образование");

    public String title;

    ContactTypes(String title) {
        this.title = title;
    }
}
