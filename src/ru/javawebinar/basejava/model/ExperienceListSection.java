package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperienceListSection extends AbstractSection {
    private final List<Experience> sectionInfo;

    public ExperienceListSection(Experience... info) {
        this.sectionInfo = new ArrayList<>();
        setToSectionInfo(info);
    }

    private void setToSectionInfo(Experience[] info) {
        sectionInfo.addAll(Arrays.asList(info));
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
