package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.habit.HabitRepository;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class HistoryManager {
    private static HistoryRepository hm;

    private final static String URL = getProperty("db.url");

    private final static String USER_NAME = getProperty("db.username");

    private final static String PASSWORD = getProperty("db.password");

    public static HistoryRepository getInstance() {
        if (hm == null) {
            hm = new DbHistoryRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return hm;
    }
}
