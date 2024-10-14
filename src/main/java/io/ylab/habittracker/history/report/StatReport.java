package io.ylab.habittracker.history.report;

import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.habit.service.HabitService;
import io.ylab.habittracker.history.service.HistoryService;

import java.time.LocalDate;

public class StatReport {
    private final HistoryService hs;
    private final HabitService habitService;

    public StatReport(HistoryService hs, HabitService habitService) {
        this.hs = hs;
        this.habitService = habitService;
    }

    public void getReport(long userId, long habitId) {
        Habit habit = habitService.getHabit(userId, habitId);
        System.out.println("Статистика выполнения привычки с id: " + habitId);
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("Максимальный streak: " + hs.getMaxStreak(userId, habitId));
        System.out.println("Текущий streak: " + hs.getCurrentStreak(userId, habitId));
        int amount = hs.generateNumberOfCompleteHabit(userId, habitId, habit.getCreateTime(), LocalDate.now());
        System.out.println("За все время привычка была выполнена: " + amount + " раз");
        System.out.println("Процент успешного выполнения за все время: " +
                hs.successRateHabit(userId, habitId, habit.getCreateTime(), LocalDate.now()) + "%");
    }
}


