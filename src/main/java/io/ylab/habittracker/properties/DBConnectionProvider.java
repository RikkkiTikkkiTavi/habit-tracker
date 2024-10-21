package io.ylab.habittracker.properties;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Класс провайдер
 *
 * @autor Константин Щеглов
 */
public class DBConnectionProvider {
    /**
     * Поле url базы данных
     */
    private final String url;
    /**
     * Поле имя пользователя базы данных
     */
    private final String username;
    /**
     * Поле пароль базы данных
     */
    private final String password;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     */
    public DBConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Метод устанавливающий соединение с базой данных
     * @return объект класса Connection
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
