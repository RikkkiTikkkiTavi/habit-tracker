package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.properties.DBConnectionProvider;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public final class UserRepoManager {

    private static UserRepository ur;

    private final static String URL = getProperty("db.url");

    private final static String USER_NAME = getProperty("db.username");

    private final static String PASSWORD = getProperty("db.password");

    public static UserRepository getInstance() {
        if (ur == null) {
            ur = new DbUserRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return ur;
    }
}


