package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.history.DbHistoryRepository;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

/**
 * Класс паттерн одиночка

 * @autor Константин Щеглов
 */
public class RoleManager {

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

    private static RoleRepository ur;

    /**
     * Метод возвращает ссылку на объект класса DbUserRoleRepository
     * @see DbUserRoleRepository
     */
    public static RoleRepository getInstance() {
        if (ur == null) {
            ur = new DbUserRoleRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD));
        }
        return ur;
    }
}
