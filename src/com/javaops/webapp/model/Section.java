package com.javaops.webapp.model;

public interface Section<T, E> {

    T getSectionStorage();

    void addContent(E content);
}
