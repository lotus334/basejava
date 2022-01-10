package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private List<String> sectionStorage;

    public ListSection(List<String> sectionStorage) {
        this.sectionStorage = sectionStorage;
    }

    public void printContent() {
        for (String item : sectionStorage) {
            System.out.println(item);
        }
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
