package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {

    private final HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
    private String dateTime;

    @Test
    void withStantartTime() {
        dateTime = "2024-02-02T14:06:10+03:00";
        assertThat("2024-02-02T14:06:10").isEqualTo(parser.parse(dateTime).toString());
    }

    @Test
    void dateTimeIsNull() {
        assertThatThrownBy(() -> parser.parse(dateTime))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Нулевое значение");
    }

    @Test
    void dateTimeIsBlank() {
        dateTime = "";
        assertThatThrownBy(() -> parser.parse(dateTime))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Пустая строка");
    }

    @Test
    void withStantartTime2() {
        dateTime = "2024-02-23T13:42:55+00:00";
        assertThat("2024-02-23T13:42:55").isEqualTo(parser.parse(dateTime).toString());
    }
}