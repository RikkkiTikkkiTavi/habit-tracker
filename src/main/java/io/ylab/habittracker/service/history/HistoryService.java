package io.ylab.habittracker.service.history;

import io.ylab.habittracker.model.history.HabitHistory;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {
    void noteHabit(long userId, long habitId);

    List<HabitHistory> getUserHistory(long userId);

    int generateNumberOfCompleteHabit(long userId, LocalDate start, LocalDate end);

    int successRateHabit(long userId, long habitId, LocalDate start, LocalDate end);

    int getCurrentUserStreak(long userId, long habitId);
}
