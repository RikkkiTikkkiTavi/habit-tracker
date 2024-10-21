package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.repository.habit.HabitRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс позволяющий сохранять привычки в оперативной памяти
 * @see HistoryRepository - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class InMemoryHistoryRepository implements HistoryRepository {

    @Override
    public LocalDate getLastNoteDate(long habitId) {
        return null;
    }

    @Override
    public List<HabitHistory> getUserHistory(long userId, LocalDate from, LocalDate to) {
        return List.of();
    }

    @Override
    public void addUserHistory(HabitHistory uh) {
    }
    @Override
    public HabitHistory getHistory(long habitId, LocalDate date) {
        return null;
    }
}

