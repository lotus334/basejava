package com.javaops.webapp.model;

import java.util.Objects;

public class TextSection implements Section {
    private String sectionStorage;

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.sectionStorage = content;
    }

    public String getSectionStorage() {
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

    @Override
    public String toString() {
        return sectionStorage;
    }
}
