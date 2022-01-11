package ru.javawebinar.basejava.model;

public class StringSection extends AbstractSection {
    private String sectionInfo;

    public StringSection(String sectionInfo) {
        this.sectionInfo = sectionInfo;
    }

    @Override
    public String toString() {
        return sectionInfo + '\n';
    }
}
