package io.ylab.habittracker.properties;

import io.ylab.habittracker.validate.ValidationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbProperties {

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

