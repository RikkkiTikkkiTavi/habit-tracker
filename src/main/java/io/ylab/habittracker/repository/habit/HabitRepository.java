package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.model.habit.Habit;

import java.util.List;

/**
 * Интерфейс предоставляющий контракт с базовыми CRUD методами для манипуляций с привычками
 *
 * @autor Константин Щеглов
 */
public interface HabitRepository {
    /**
     * Функция создания привычки
     * @return - ссылку на созданную привычку
     */
    Habit createHabit(Habit habit);
    /**
     * Функция обновления привычки
     * @return - ссылка на обновленную привычку
     */
    Habit updateHabit(Habit habit);
    /**
     * Функция получения привычки по id
     * @param habitId - id привычки
     * @return - ссылку на полученную привычку
     */
    Habit getHabit(long habitId);
    /**
     * Функция удаления привычки
     */
    void deleteHabit(long userId, long habitId);
    /**
     * Функция получения списка привычек пользователя с id
     * @param userId
     */
    List<Habit> getUserHabits(long userId);
}
