package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HabrCareerDateTimeParser implements DateTimeParser {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @Override
    public LocalDateTime parse(String parse) {
        if (parse == null) {
            throw new NullPointerException("Нулевое значение");
        }
        if (parse.isBlank() || parse.isEmpty()) {
            throw new NullPointerException("Пустая строка");
        }
        return LocalDateTime.parse(parse, formatter);
    }

}
