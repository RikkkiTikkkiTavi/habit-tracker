package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.properties.DBConnectionProvider;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

public class HabitRepoManager {
    /**
     * Класс паттерн одиночка

     * @autor Константин Щеглов
     */
    private HabitRepoManager() {
    }

    private static HabitRepository hr;

    /**
     * Поле url базы данных
     */
    private final static String URL = getProperty("db.url");
    /**
     * Поле имя пользователя базы банных
     */
    private final static String USER_NAME = getProperty("db.username");
    /**
     * Поле пароль для доступа к базе данных
     */
    private final static String PASSWORD = getProperty("db.password");

    /**
     * Метод возвращает ссылку на объект класса DbHabitRepository
     * @see DbHabitRepository
     */
    public static HabitRepository getInstance() {
        if (hr == null) {
            hr = new DbHabitRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return hr;
    }
}
