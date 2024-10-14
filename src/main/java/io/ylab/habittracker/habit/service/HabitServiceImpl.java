package io.ylab.habittracker.habit.service;

import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.habit.repo.HabitRepoManager;
import io.ylab.habittracker.habit.repo.HabitRepository;
import io.ylab.habittracker.validate.NotFoundException;
import io.ylab.habittracker.validate.Validate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Habit habit = hr.getHabit(userId, habitId);
        Validate.checkHabit(habit);
        return habit;
    }

    @Override
    public List<Habit> getHabits(long userId) {
        Map<Long, Habit> userHabits = hr.getUserHabits(userId);
        if (userHabits == null) {
            throw new NotFoundException("У пользователя нету привычек!");
        }
        return new ArrayList<>(userHabits.values());
    }
}
