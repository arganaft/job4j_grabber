package ru.job4j.gc.leak;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGenerator implements Generate {

    public final String pathNames = "src/main/java/ru/job4j/gc/leak/files/names.txt";
    public final String pathSurnames = "src/main/java/ru/job4j/gc/leak/files/surnames.txt";
    public final String pathPatrons = "src/main/java/ru/job4j/gc/leak/files/patr.txt";
    public final int newUsers = 1000;

    public List<String> names;
    public List<String> surnames;
    public List<String> patrons;
    private List<User> users = new ArrayList<>();
    private Random random;

    public UserGenerator(Random random) {
        this.random = random;
        readAll();
    }

    @Override
    public void generate() {
        users.clear();
        for (int i = 0; i < newUsers; i++) {
            users.add(new User(String.format("%s %s %s",
                    surnames.get(random.nextInt(surnames.size())),
                    names.get(random.nextInt(names.size())),
                    patrons.get(random.nextInt(patrons.size())))));
        }
    }

    private void readAll() {
        try {
            names = read(pathNames);
            surnames = read(pathSurnames);
            patrons = read(pathPatrons);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User randomUser() {
        return users.get(random.nextInt(users.size()));
    }

    public  List<User> getUsers() {
        return users;
    }
}
