package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> sectionInfo;

    public ListSection(String ... strings) {
        this.sectionInfo = new ArrayList<>();
        for (String info : strings) {
            sectionInfo.add(info);
        }
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
