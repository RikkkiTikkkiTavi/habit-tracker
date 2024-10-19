package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.properties.DBConnectionProvider;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class HabitRepoManager {
    private HabitRepoManager() {
    }

    private static HabitRepository hr;

    private final static String URL = getProperty("db.url");

    private final static String USER_NAME = getProperty("db.username");

    private final static String PASSWORD = getProperty("db.password");

    public static HabitRepository getInstance() {
        if (hr == null) {
            hr = new DbHabitRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return hr;
    }
}
