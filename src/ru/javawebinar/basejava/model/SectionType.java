package ru.javawebinar.basejava.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),    // TextSection
    ACHIEVEMENT("Достижения"),      // ListSection
    QUALIFICATIONS("Квалификация"), // ListSection
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
