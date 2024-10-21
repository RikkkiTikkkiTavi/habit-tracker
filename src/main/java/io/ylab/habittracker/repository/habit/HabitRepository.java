package io.ylab.habittracker.repository.habit;

import io.ylab.habittracker.model.habit.Habit;

import java.util.List;

/**
 * Интерфейс
 * @autor Константин Щеглов
 */
public interface HabitRepository {
    Habit createHabit(Habit habit);
    Habit updateHabit(Habit habit);
    Habit getHabit(long habitId);
    void deleteHabit(long userId, long habitId);

    List<Habit> getUserHabits(long userId);
}
