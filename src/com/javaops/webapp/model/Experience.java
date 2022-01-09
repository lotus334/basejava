package com.javaops.webapp.model;

import java.util.List;
import java.util.Objects;

public class Experience {
    private String title;
    private String link;
    List<Position> positions;

    public Experience(String title, String link, List<Position> positions) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(positions, "positions must not be null");
        this.title = title;
        this.link = link;
        this.positions = positions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
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

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
