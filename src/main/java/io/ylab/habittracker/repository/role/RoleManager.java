package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.properties.DBConnectionProvider;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class RoleManager {

    private final static String URL = getProperty("db.url");

    private final static String USER_NAME = getProperty("db.username");

    private final static String PASSWORD = getProperty("db.password");

    private static RoleRepository ur;

    public static RoleRepository getInstance() {
        if (ur == null) {
            ur = new DbUserRoleRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return ur;
    }
}
