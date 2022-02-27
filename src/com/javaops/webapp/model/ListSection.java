package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<String> skills;

    public ListSection() {}

    public ListSection(List<String> skills) {
        Objects.requireNonNull(skills, "skills must not be null");
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return skills.equals(that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "skills=" + skills +
                '}';
    }
}
