package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Position {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return startDate.equals(position.startDate) && endDate.equals(position.endDate) &&
                title.equals(position.title) && Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        StringBuilder string = new StringBuilder();
        string.append(startDate.format(formatter)).append(" - ");
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
