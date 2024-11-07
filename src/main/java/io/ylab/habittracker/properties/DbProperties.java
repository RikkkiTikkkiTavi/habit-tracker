package io.ylab.habittracker.properties;

import io.ylab.habittracker.validate.ValidationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * Класс утилита для работы с конфигом
 *
 * @autor Константин Щеглов
 */
public class DbProperties {

    /**
     * Метод работающий с конфигом, использует потоки ввода/вывода
     * @return - строковое представление данных из конфига
     */
    public static String getProperty(String key) {
        FileInputStream fis;
        Properties property = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            return property.getProperty(key);
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }
}

