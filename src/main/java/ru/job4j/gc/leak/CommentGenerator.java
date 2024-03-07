package ru.job4j.gc.leak;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentGenerator implements Generate {

    public final String pathPhrases = "src/main/java/ru/job4j/gc/leak/files/phrases.txt";

    public final String separator = System.lineSeparator();
    private List<Comment> comments = new ArrayList<>();
    private List<String> phrases;
    private UserGenerator userGenerator;
    private Random random;

    public CommentGenerator(Random random, UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
        this.random = random;
        read();
    }

    private void read() {
        try {
            phrases = read(pathPhrases);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void generate() {
        comments.clear();
        for (int i = 0; i < 50; i++) {
            comments.add(new Comment(
                    String.format("%s%s%s%s%s",
                            phrases.get(random.nextInt(phrases.size())), separator,
                            phrases.get(random.nextInt(phrases.size())), separator,
                            phrases.get(random.nextInt(phrases.size()))),
                    userGenerator.randomUser()));
        }
    }
}