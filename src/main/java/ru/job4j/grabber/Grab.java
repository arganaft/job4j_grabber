package ru.job4j.grabber;

import org.quartz.SchedulerException;

import java.io.IOException;

public interface Grab {
    void init() throws SchedulerException, IOException;
}
