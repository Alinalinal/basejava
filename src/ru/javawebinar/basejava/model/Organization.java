package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<Position> positions;

    public Organization(String name, String url, List<Position> positions) {
        Objects.requireNonNull(positions, "positions must not be null");
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(homePage.getName().toUpperCase()).append('\n');
        for (Position position : positions) {
            string.append(position);
        }
        return string.toString();
    }
}
