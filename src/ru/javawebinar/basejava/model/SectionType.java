package ru.javawebinar.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества"),    // TextSection
    OBJECTIVE("Позиция"),           // TextSection
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
