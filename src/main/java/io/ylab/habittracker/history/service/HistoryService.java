package io.ylab.habittracker.history.service;

import io.ylab.habittracker.history.model.UserHistory;

import java.time.LocalDate;

public interface HistoryService {
    void noteHabit(long userId, long habitId);

    UserHistory getUserHistory(long userId);

    int generateNumberOfCompleteHabit(long userId, long habitId, LocalDate start, LocalDate end);

    int successRateHabit(long userId, long habitId, LocalDate start, LocalDate end);

    int getMaxStreak(long userId, long habitId);

    int getCurrentStreak(long userId, long habitId);

}
