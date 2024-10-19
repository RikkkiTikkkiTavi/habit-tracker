package io.ylab.habittracker.repository.history;

import io.ylab.habittracker.model.history.HabitHistory;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository {

    void addUserHistory(HabitHistory uh);

    List<HabitHistory> getUserHistory(long userId, LocalDate from, LocalDate to);

    HabitHistory getHistory(long habitId, LocalDate date);

    LocalDate getLastNoteDate(long habitId);
}
