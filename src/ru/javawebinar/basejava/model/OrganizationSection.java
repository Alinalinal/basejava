package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> content;

    public OrganizationSection(List<Organization> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public List<Organization> getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection that = (OrganizationSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Organization info : content) {
            string.append(info).append('\n');
        }
        return string.toString();
    }
}