package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts;
    private final Map<SectionType, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new EnumMap<>(ContactType.class);
        this.sections = new EnumMap<>(SectionType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void addContact(ContactType type, String info) {
        contacts.put(type, info);
    }

    public void addSection(SectionType type, AbstractSection info) {
        sections.put(type, info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return getUuid().equals(resume.getUuid()) && getFullName().equals(resume.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName());
    }

    @Override
    public String toString() {
        return "Resume: uuid = " + uuid + " (fullName = '" + fullName + "')";
    }
}
