package io.ylab.habittracker.user.service;

import io.ylab.habittracker.user.model.User;
import io.ylab.habittracker.validate.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserService us;
    User user;

    @BeforeEach
    void setUp() {
        us = new UserServiceImpl();
        user = us.createUser("name", "email@");
    }

    @Test
    void createUserWithInvalidEmailThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> us.createUser("name", "email"));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
    }

    @Test
    void createUserWithInvalidNameThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> us.createUser("", "@email@"));
        assertEquals("Имя не может быть пустым и должно содержать не более 20 символов", e.getMessage());
    }

    @Test
    void createUserWithAlreadyExistingEmailThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> us.createUser("e", "email@"));
        assertEquals("Пользователь с таким email уже существует!", e.getMessage());
    }

    @Test
    void updateUserWithAlreadyExistingEmailThrowException() {
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> us.updateUser(new User("sa","@j"), "e", "email@"));
        assertEquals("Email занят!", e.getMessage());
    }

    @Test
    void updateUserReturnUser() {
        assertEquals(user, us.updateUser(user, "user", "newEmail@"));
    }

    @Test
    void deleteUserReturnLink() {
        assertEquals(user, us.deleteUser("email@"));
    }

   @AfterEach
    void after() {
        us.deleteUser("email@");
   }
}