package io.ylab.habittracker.service.history;

import io.ylab.habittracker.model.habit.Frequency;
import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.repository.habit.HabitRepoManager;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.repository.history.HistoryManager;
import io.ylab.habittracker.repository.history.HistoryRepository;
import io.ylab.habittracker.validate.Validate;
import io.ylab.habittracker.validate.ValidationException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository hr = HistoryManager.getInstance();
    private final HabitRepository habitRepository = HabitRepoManager.getInstance();

    @Override
    public void noteHabit(long userId, long habitId) {
        Habit habit = habitRepository.getHabit(habitId);

        if (habit == null) {
            throw new ValidationException("Привычка не найдена");
        }

        HabitHistory habitHistory = new HabitHistory(habitId, userId, LocalDate.now());

        HabitHistory previousHistory =
                hr.getHistory(habitId, LocalDate.now().minusDays(1));

        List<HabitHistory> previousWeekHistory =
                hr.getUserHistory(userId, LocalDate.now().minusDays(7), LocalDate.now());

        if (habit.getFrequency().equals(Frequency.EVERY_DAY)) {
            if (previousHistory == null) {
                hr.addUserHistory(habitHistory);
            } else {
                habitHistory.setStreak(previousHistory.getStreak() + 1);
                hr.addUserHistory(habitHistory);
            }
        } else {
            if (previousWeekHistory.isEmpty()) {
                hr.addUserHistory(habitHistory);
            } else {
                int size = previousWeekHistory.size();
                habitHistory.setStreak(previousWeekHistory.get(size-1).getStreak() + 1);
                hr.addUserHistory(habitHistory);
            }
        }
    }

    @Override
    public List<HabitHistory> getUserHistory(long userId) {
        return hr.getUserHistory(userId, LocalDate.now().minusYears(10), LocalDate.now());
    }

    @Override
    public int generateNumberOfCompleteHabit(long userId, LocalDate start, LocalDate end) {
        Validate.checkPeriod(start, end);
        return hr.getUserHistory(userId, start, end).size();
    }

    @Override
    public int successRateHabit(long userId, long habitId, LocalDate start, LocalDate end) {
        Validate.checkPeriod(start, end);
        long period = ChronoUnit.DAYS.between(start, end.plusDays(1));
        return (int) (100 * (generateNumberOfCompleteHabit(userId, start, end.plusDays(1)) / period));
    }

    @Override
    public int getCurrentUserStreak(long userId, long habitId) {
        int streak = 0;
        LocalDate lastDate = hr.getLastNoteDate(habitId);
        if (lastDate != null) {
            if (lastDate.equals(LocalDate.now().minusDays(1)) || lastDate.equals(LocalDate.now())) {
                streak = hr.getHistory(habitId, lastDate).getStreak();
            }
        }
        return streak;
    }
}