package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private List<String> sectionStorage;

    public ListSection(List<String> sectionStorage) {
        Objects.requireNonNull(sectionStorage, "sectionStorage must not be null");
        this.sectionStorage = sectionStorage;
    }

    public List<String> getSectionStorage() {
        return sectionStorage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return sectionStorage.equals(that.sectionStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionStorage);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "sectionStorage=" + sectionStorage +
                '}';
    }
}
