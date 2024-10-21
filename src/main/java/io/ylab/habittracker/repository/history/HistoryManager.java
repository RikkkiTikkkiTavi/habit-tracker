package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.habit.HabitRepository;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

/**
 * Класс паттерн одиночка

 * @autor Константин Щеглов
 */
public class HistoryManager {

    private static HistoryRepository hm;
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
     * Метод возвращает ссылку на объект класса DbHistoryRepository
     * @see DbHistoryRepository
     */
    public static HistoryRepository getInstance() {
        if (hm == null) {
            hm = new DbHistoryRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return hm;
    }
}
