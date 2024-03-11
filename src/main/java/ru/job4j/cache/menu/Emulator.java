package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Emulator {
    DirFileCache dirFileCache;

    public void setCacheDirectory(File directory) {
        dirFileCache = new DirFileCache(directory.toString());
    }

    public void loadFileToCache(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            char[] buffer = new char[8192];

            int charsRead;
            while ((charsRead = reader.read(buffer, 0, buffer.length)) != -1) {
                stringBuilder.append(buffer, 0, charsRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dirFileCache.put(file.getName().toString(), stringBuilder.toString());
    }

    public  String getFromCache(File file) {
        return dirFileCache.get(file.toString());
    }

}
