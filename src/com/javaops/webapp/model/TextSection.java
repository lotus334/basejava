package com.javaops.webapp.model;

import java.util.Objects;

public class TextSection implements Section<String> {
    private String sectionStorage;

    public TextSection(String content) {
        this.sectionStorage = content;
    }

    @Override
    public String getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public void addContent(String content) {
        this.sectionStorage = content;
    }

    @Override
    public String toString() {
        return sectionStorage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return sectionStorage.equals(that.sectionStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionStorage);
    }
}
