package io.ylab.habittracker;

import io.ylab.habittracker.menu.MainMenu;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class Main {
    private final static String URL = getProperty("db.url");

    private final static String USER_NAME = getProperty("db.username");

    private final static String PASSWORD = getProperty("db.password");

    public static void main(String[] args) {
        LiquibaseScripts.setChangeSet(URL, USER_NAME, PASSWORD);
        MainMenu.startMenu();
    }
}