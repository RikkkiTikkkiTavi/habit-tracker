package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс предоставляющий контракт с базовыми CRUD методами для манипуляций с историей привычек
 *
 * @autor Константин Щеглов
 */
public interface HistoryRepository {

    /**
     * Функция создания истории
     */
    void addUserHistory(HabitHistory uh);
    /**
     * Функция получения истории пользователя с id за определенный период
     * @param userId
     */
    List<HabitHistory> getUserHistory(long userId, LocalDate from, LocalDate to);
    /**
     * Функция получения истории привычки по id
     * @param habitId - id привычки
     * @param date - даты выполнения привычки
     */
    HabitHistory getHistory(long habitId, LocalDate date);
    /**
     * Функция получения даты последней отмеченной привычки
     * @param habitId - id привычки
     */
    LocalDate getLastNoteDate(long habitId);
}
