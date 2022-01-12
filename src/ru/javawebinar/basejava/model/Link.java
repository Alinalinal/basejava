package ru.javawebinar.basejava.model;

public class Link {
    private String title;
    private String link;

    public Link(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return title;
    }
}
