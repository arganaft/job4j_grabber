package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }


    @Override
    protected String load(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(String.format("%s/%s", cachingDir, key)))) {
            char[] buffer = new char[8192];

            int charsRead;
            while ((charsRead = reader.read(buffer, 0, buffer.length)) != -1) {
                stringBuilder.append(buffer, 0, charsRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        put(key, stringBuilder.toString());
        return stringBuilder.toString();
    }

}
