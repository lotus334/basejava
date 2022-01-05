package com.javaops.webapp.model;

public enum ContactTypes {

    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Электронная почта"),
    LINKEDIN("Линкед ин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стэк оверфлоу"),
    HOMEPAGE("Домашняя страница");

    public String title;

    ContactTypes(String title) {
        this.title = title;
    }
}
