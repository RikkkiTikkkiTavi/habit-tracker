package io.ylab.habittracker;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LiquibaseScripts {

//    private static final String URL = "jdbc:postgresql://localhost:5432/habit-db";
//
//    private static final String USER_NAME = "postgres";
//
//    private static final String PASSWORD = "postgres";


    public static void setChangeSet(String url, String userName, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db.changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (SQLException | LiquibaseException e) {
            System.out.println(e.getMessage());
        }
    }

}
