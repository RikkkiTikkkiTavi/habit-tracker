package io.ylab.habittracker.habit.service;

import io.ylab.habittracker.habit.model.Habit;

import java.util.List;

public interface HabitService {
    Habit addHabit(Habit habit, long userId);
    Habit editHabit(Habit habit);
    void deleteHabit(long userId, long habitId);
    Habit getHabit(long userId, long habitId);
    List<Habit> getHabits(long userId);
}
