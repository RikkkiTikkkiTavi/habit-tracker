package io.ylab.habittracker.repository.role;

import io.ylab.habittracker.model.user.Role;

/**
 * Интерфейс предоставляет пару методов для работы с ролями пользователей
 *
 * @autor Константин Щеглов
 */
public interface RoleRepository {
    /**
     * Функция присваивания пользователю роли
     */
    void addUserRole(long userId, long roleId);
    /**
     * Функция получения роли пользователя
     */
    Role getUserRole(long userId);
}
