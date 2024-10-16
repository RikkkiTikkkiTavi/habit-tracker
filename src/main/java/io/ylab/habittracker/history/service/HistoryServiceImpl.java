package io.ylab.habittracker.history.service;

import io.ylab.habittracker.history.model.UserHistory;
import io.ylab.habittracker.history.model.HabitHistory;
import io.ylab.habittracker.history.repo.HistoryRepository;
import io.ylab.habittracker.history.repo.InMemoryHistoryRepository;
import io.ylab.habittracker.validate.Validate;
import io.ylab.habittracker.validate.ValidationException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository hr = new InMemoryHistoryRepository();

    @Override
    public void noteHabit(long userId, long habitId) {
        UserHistory uh = hr.getUserHistory(userId);
        if (uh == null) {
            uh = new UserHistory();
            hr.addUserHistory(userId, uh);
        }
            Map<Long, HabitHistory> ch = uh.getCompletedHabits();
            HabitHistory hh;
            if (ch.containsKey(habitId)) {
                hh = ch.get(habitId);
                hh.setDate(LocalDate.now());
            } else {
                hh = new HabitHistory();
                hh.setDate(LocalDate.now());
                ch.put(habitId, hh);
            }

        addStreak(getHabitHistory(userId, habitId));
    }

    @Override
    public int generateNumberOfCompleteHabit(long userId, long habitId, LocalDate start, LocalDate end) {
        Validate.checkPeriod(start, end);
        SortedSet<LocalDate> dates = getHabitHistory(userId, habitId).getCompletedDates();
        return dates.subSet(start, end.plusDays(1)).size();
    }

    @Override
    public int successRateHabit(long userId, long habitId, LocalDate start, LocalDate end) {
        Validate.checkPeriod(start, end);
        long period = ChronoUnit.DAYS.between(start, end.plusDays(1));
        return (int) (100 * (generateNumberOfCompleteHabit(userId, habitId, start, end.plusDays(1))/period));
    }

    @Override
    public int getMaxStreak(long userId, long habitId) {
        return getHabitHistory(userId, habitId).getMaxStreak();
    }

    @Override
    public int getCurrentStreak(long userId, long habitId) {
        return getHabitHistory(userId, habitId).getCurrentStreak();
    }

    @Override
    public UserHistory getUserHistory(long userId) {
        return hr.getUserHistory(userId);
    }

    private HabitHistory getHabitHistory(long userId, long habitId) {
        UserHistory uh = hr.getUserHistory(userId);
        if (uh != null) {
            Map<Long, HabitHistory> hh = uh.getCompletedHabits();
            if (!hh.containsKey(habitId)) {
                hh.put(habitId, new HabitHistory());
            }
            return hh.get(habitId);
        } else {
            throw new ValidationException("Вы не выполнили ни одной привычки");
        }
    }

    private void addStreak(HabitHistory habit) {
        if (habit.getCurrentStreak() == 0) {
            habit.setCurrentStreak(1);
            habit.setMaxStreak(1);
            habit.setLastNote(LocalDate.now());
        } else {
            int currentStreak = habit.getCurrentStreak();
            int maxStreak = habit.getMaxStreak();

            boolean condition = habit.getLastNote().plusDays(1).equals(LocalDate.now());

            if (condition) {
                currentStreak++;
                habit.setCurrentStreak(currentStreak);
                if (currentStreak > maxStreak) {
                    habit.setMaxStreak(currentStreak);
                }
            } else {
                habit.setCurrentStreak(1);
            }
        }
    }
}
