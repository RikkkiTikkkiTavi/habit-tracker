package io.ylab.habittracker.service.history;

import io.ylab.habittracker.model.history.HabitHistory;
import io.ylab.habittracker.repository.history.HistoryManager;
import io.ylab.habittracker.repository.history.HistoryRepository;
import io.ylab.habittracker.validate.Validate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository hr = HistoryManager.getInstance();

    @Override
    public void noteHabit(long userId, long habitId) {
        HabitHistory habitHistory = new HabitHistory(habitId, userId, LocalDate.now());
        HabitHistory previousHistory = hr.getHistory(habitId, LocalDate.now().minusDays(1));
        if (previousHistory == null) {
            hr.addUserHistory(habitHistory);
        } else {
            habitHistory.setStreak(previousHistory.getStreak() + 1);
            hr.addUserHistory(habitHistory);
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
            } else {
                streak = 1;
            }
        }
        return streak;
    }
}