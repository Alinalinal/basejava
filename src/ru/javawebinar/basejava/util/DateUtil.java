package ru.javawebinar.basejava.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static void writeAsData(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    public static LocalDate readDataDate(DataInputStream dis) throws IOException {
        return of(dis.readInt(), Month.of(dis.readInt()));
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Ceйчас" : date.format(FORMATTER);
    }

    public static LocalDate format(String date) {
        if (date.trim().equals("Сейчас".toLowerCase(Locale.ROOT))) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date.trim(), FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
