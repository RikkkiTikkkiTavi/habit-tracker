package io.ylab.habittracker.model.user;

/**
 * Enum со значениями статуса пользователя.
 * AVAILABLE - пользователю доступен весь функционал согласно его роли
 * BLOCKED - пользователь лишен доступа к приложению
 * @autor Константин Щеглов
 */
public enum Status {
    BLOCKED, AVAILABLE;
}
