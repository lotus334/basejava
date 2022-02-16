package com.javaops.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Position> positions;
    private Link homePage;

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(String name, String url, List<Position> positions) {
        this(new Link(name, url), positions);
    }

    public Organization(Link homePage, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return positions.equals(that.positions) && homePage.equals(that.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions, homePage);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "positions=" + positions +
                ", homePage=" + homePage +
                '}';
    }
}
