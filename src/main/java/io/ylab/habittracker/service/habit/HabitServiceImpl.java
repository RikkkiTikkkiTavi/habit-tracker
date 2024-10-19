package io.ylab.habittracker.service.habit;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.repository.habit.HabitRepoManager;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.validate.Validate;

import java.time.LocalDate;
import java.util.List;

public class HabitServiceImpl implements HabitService {

    HabitRepository hr = HabitRepoManager.getInstance();


    @Override
    public Habit addHabit(Habit habit, long userId) {
        Validate.checkHabit(habit);
        habit.setCreateTime(LocalDate.now());
        return hr.createHabit(habit);
    }

    @Override
    public Habit editHabit(Habit habit) {
        Habit updatedHabit = hr.updateHabit(habit);
        Validate.checkHabit(updatedHabit);
        return updatedHabit;
    }

    @Override
    public void deleteHabit(long userId, long habitId) {
        hr.deleteHabit(userId, habitId);
    }

    @Override
    public Habit getHabit(long userId, long habitId) {
        Habit habit = hr.getHabit( habitId);
        Validate.checkHabit(habit);
        return habit;
    }

    @Override
    public List<Habit> getHabits(long userId) {
        List<Habit> userHabits = hr.getUserHabits(userId);
        if (userHabits == null) {
            return List.of();
        }
        return userHabits;
    }
}
