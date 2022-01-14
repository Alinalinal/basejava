package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = new Link(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) && startDate.equals(that.startDate) && endDate.equals(that.endDate) &&
                title.equals(that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        string.append(homePage.getName().toUpperCase()).append('\n').append(startDate.format(formatter)).append(" - ");
        if (endDate.equals(LocalDate.now())) {
            string.append("Сейчас");
        } else {
            string.append(endDate.format(formatter));
        }
        string.append('\n').append(title).append('\n');
        if (description != null) {
            string.append(description).append('\n');
        }
        return string.toString();
    }
}
