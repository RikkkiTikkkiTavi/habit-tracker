package io.ylab.habittracker.habit.repo;

import io.ylab.habittracker.habit.model.Habit;

import java.util.Map;

public interface HabitRepository {
    Habit createHabit(Habit habit);
    Habit updateHabit(Habit habit);
    Habit getHabit(long userId, long habitId);
    void deleteHabit(long userId, long habitId);

    Map<Long, Habit> getUserHabits(long userId);
}
