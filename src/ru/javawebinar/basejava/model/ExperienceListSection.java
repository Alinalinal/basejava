package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ExperienceListSection extends AbstractSection {
    private List<Experience> sectionInfo;

    public ExperienceListSection(Experience ... experiences) {
        this.sectionInfo = new ArrayList<>();
        for (Experience info : experiences) {
            sectionInfo.add(info);
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Experience info : sectionInfo) {
            string.append(info).append('\n');
        }
        return string.toString();
    }
}
