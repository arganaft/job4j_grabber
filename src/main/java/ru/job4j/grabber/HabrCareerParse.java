package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private final String prefix = "/vacancies?page=";
    private final String suffix = "&q=Java%20developer&type=all";
    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new LinkedList<>();
        int pageNumber = 1;
        Connection connection;
        Document document;
        while (pageNumber <= 5) {
            String fullLink = "%s%s%d%s".formatted(link, prefix, pageNumber, suffix);
            connection = Jsoup.connect(fullLink);
            document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            for (Element row : rows) {
                posts.add(createPost(row, link));
            }
            pageNumber++;
        }
        return posts;
    }

    private Post createPost(Element row, String link) {
      Element titleElement = row.select(".vacancy-card__title").first();
      Element dateElement = row.select(".vacancy-card__date").first();
      String date = dateElement.select("time").attr("datetime");
      Element linkElement = titleElement.child(0);
      String vacancyName = titleElement.text();
      String vacancyLink = String.format("%s%s", link, linkElement.attr("href"));
      String description;
      try {
          description = retrieveDescription(vacancyLink);
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
      return new Post(vacancyName, vacancyLink, description, dateTimeParser.parse(date));
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