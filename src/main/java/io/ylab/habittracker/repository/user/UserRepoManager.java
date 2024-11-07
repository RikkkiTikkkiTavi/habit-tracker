package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.properties.DBConnectionProvider;
import io.ylab.habittracker.repository.role.DbUserRoleRepository;
import io.ylab.habittracker.repository.role.RoleManager;

import static io.ylab.habittracker.properties.DbProperties.getProperty;

/**
 * Класс паттерн одиночка

 * @autor Константин Щеглов
 */
public final class UserRepoManager {


    private static UserRepository ur;

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
     * Метод возвращает ссылку на объект класса DbUserRoleRepository
     * @see DbUserRepository
     */
    public static UserRepository getInstance() {
        if (ur == null) {
            ur = new DbUserRepository(new DBConnectionProvider(URL, USER_NAME, PASSWORD), RoleManager.getInstance());
        }
        return ur;
    }
}


