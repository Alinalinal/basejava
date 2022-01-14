package ru.javawebinar.basejava.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
    private String content;

    public StringSection(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringSection)) return false;
        StringSection that = (StringSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
    @Override
    public String toString() {
        return content + '\n';
    }
}
