package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";

    public static void main(String[] args) throws IOException {
        int pageNumber = 1;
        Connection connection;
        Document document;
        while (pageNumber <= 5) {
            String fullLink = "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, pageNumber, SUFFIX);
            connection = Jsoup.connect(fullLink);
            document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();

                Element dateElement = row.select(".vacancy-card__date").first();
                String date = dateElement.select("time").attr("datetime");

                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String description;
                try {
                    description = new HabrCareerParse().retrieveDescription(link);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("%s %s date - %s%n", vacancyName, link, date);
                System.out.println(description);
            });
            pageNumber++;
        }

    }

    private String retrieveDescription(String link) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = Jsoup.connect(link);
        Document document = connection.get();
        Element description = document.selectFirst(".vacancy-description__text");
        stringBuilder = visitingAllChildren(stringBuilder, description);
        return stringBuilder.toString();
    }


    private StringBuilder visitingAllChildren(StringBuilder stringBuilder, Element description) {
        for (Element child : description.children()) {
            if (child.children().size() == 0) {
                stringBuilder.append(child.text());
                stringBuilder.append(System.lineSeparator());
            } else {
                visitingAllChildren(stringBuilder, child);
            }
        }
        return stringBuilder;
    }
}