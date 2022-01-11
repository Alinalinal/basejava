package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKED_IN("Профиль LinkedIn"),
    GIT_HUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
