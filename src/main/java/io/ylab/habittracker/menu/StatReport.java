package io.ylab.habittracker.menu;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.service.habit.HabitService;
import io.ylab.habittracker.service.history.HistoryService;
import io.ylab.habittracker.service.history.HistoryServiceImpl;

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
        System.out.println("Текущий streak: " + hs.getCurrentUserStreak(userId, habitId));
        int amount = hs.generateNumberOfCompleteHabit(userId, habit.getCreateTime(), LocalDate.now());
        System.out.println("За все время привычка была выполнена: " + amount + " раз");
        System.out.println("Процент успешного выполнения за все время: " +
                hs.successRateHabit(userId, habitId, habit.getCreateTime(), LocalDate.now()) + "%");
    }

    public static void main(String[] args) {
        HistoryService hs = new HistoryServiceImpl();
        System.out.println("Текущий streak: " + hs.getCurrentUserStreak(1, 3));
    }
}


