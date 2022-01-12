package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection {
    private final List<String> sectionInfo;

    public ListSection(String... info) {
        this.sectionInfo = new ArrayList<>();
        setToSectionInfo(info);
    }

    private void setToSectionInfo(String[] info) {
        sectionInfo.addAll(Arrays.asList(info));
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String s : sectionInfo) {
            string.append("- ").append(s).append("\n");
        }
        return string.toString();
    }
}
