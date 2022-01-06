package com.javaops.webapp.model;

public interface Section<T> {

    T getSectionStorage();

    void addContent(T content);
}
