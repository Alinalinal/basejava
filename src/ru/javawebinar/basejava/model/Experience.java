package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Experience {
    private final Link name;
    private final LocalDate fromDate;
    private final LocalDate tillDate;
    private final String headInfo;
    private final String additionalInfo;

    public Experience(Link name, LocalDate fromDate, LocalDate tillDate, String headInfo) {
        this(name, fromDate, tillDate, headInfo, "");
    }

    public Experience(Link name, LocalDate fromDate, LocalDate tillDate, String headInfo, String additionalInfo) {
        this.name = name;
        this.fromDate = fromDate;
        this.tillDate = tillDate;
        this.headInfo = headInfo;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        string.append(name.getTitle().toUpperCase()).append('\n').append(fromDate.format(formatter)).append(" - ");
        if (tillDate.equals(LocalDate.now())) {
            string.append("Сейчас");
        } else {
            string.append(tillDate.format(formatter));
        }
        string.append('\n').append(headInfo).append('\n');
        if (!additionalInfo.equals("")) {
            string.append(additionalInfo).append('\n');
        }
        return string.toString();
    }
}
