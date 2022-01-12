package ru.javawebinar.basejava.model;

public class StringSection extends AbstractSection {
    private String sectionInfo;

    public StringSection(String info) {
        setToSectionInfo(info);
    }

    private void setToSectionInfo(String info) {
        this.sectionInfo = info;
    }

    @Override
    public String toString() {
        return sectionInfo + '\n';
    }
}
