package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;
    private String link;
    List<Position> positions;

    public Organization(String title, String link, List<Position> positions) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(positions, "positions must not be null");
        this.title = title;
        this.link = link;
        this.positions = positions;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return title.equals(that.title) && Objects.equals(link, that.link) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, positions);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", positions=" + positions +
                '}';
    }
}
