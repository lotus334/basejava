package com.javaops.webapp.model;

public enum ContactTypes {

    TELEPHONE("Телефон"),
    SKYPE("Скайп"),
    EMAIL("Электронная почта"),
    LINKEDIN("Линкед ин"),
    GITHUB("Гитхаб"),
    STACKOVERFLOW("Стэк оверфлоу"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
