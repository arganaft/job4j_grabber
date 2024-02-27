package ru.job4j.grabber;

import org.quartz.SchedulerException;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.List;

public class Parser implements Grab {

    public static void main(String[] args) throws SchedulerException, IOException {
        Grab parser = new Parser();
        parser.init();
    }

    @Override
    public void init() throws SchedulerException, IOException {
        DateTimeParser dateTimeParser = new HabrCareerDateTimeParser();
        Parse parse = new HabrCareerParse(dateTimeParser);
        String link = "https://career.habr.com";
        List<Post> posts = parse.list(link);
        Store store = new MemStore();
        for (Post post : posts) {
            store.save(post);
        }

        System.out.println(store.findById(1).toString());

        for (Post post : store.getAll()) {
            System.out.println(post.toString());
        }

    }
}
