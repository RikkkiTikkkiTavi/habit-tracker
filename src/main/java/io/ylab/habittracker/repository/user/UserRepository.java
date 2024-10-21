package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.model.user.User;

import java.util.List;

/**
 * Интерфейс предоставляющий контракт с базовыми CRUD методами для манипуляций с пользователями
 *
 * @autor Константин Щеглов
 */
public interface UserRepository {
    /**
     * Функция создания пользователя
     * @return - ссылку на созданного пользователя
     */
    User addUser(User user);

    /**
     * Функция удаления пользователя
     */
    void deleteUser(String email);

    /**
     * Функция обновления пользователя
     * @return - ссылка на обновленного пользователя
     */
    User updateUser(User user);

    /**
     * Функция получения пользователя
     * @return - ссылка на пользователя
     */
    User getUser(String email);

    /**
     * Функция получения списка всех пользователей
     */
    List<User> getUsers();
}
