package io.ylab.habittracker.service.habit;

import io.ylab.habittracker.model.habit.Habit;

import java.util.List;

public interface HabitService {
    Habit addHabit(Habit habit, long userId);

    Habit editHabit(Habit habit, long userId);

    void deleteHabit(long userId, long habitId);

    Habit getHabit(long userId, long habitId);

    List<Habit> getHabits(long userId);
}
