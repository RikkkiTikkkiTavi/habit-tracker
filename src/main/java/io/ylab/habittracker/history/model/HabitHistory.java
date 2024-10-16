package io.ylab.habittracker.history.model;

import java.time.LocalDate;
import java.util.TreeSet;

public class HabitHistory {

    private final TreeSet<LocalDate> completedDates;

    private int maxStreak;

    private int currentStreak;

    private LocalDate lastNote;

    public LocalDate getLastNote() {
        return lastNote;
    }

    public void setLastNote(LocalDate lastNote) {
        this.lastNote = lastNote;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public HabitHistory() {
        this.completedDates = new TreeSet<>();
    }

    public TreeSet<LocalDate> getCompletedDates() {
        return completedDates;
    }

    public void setDate(LocalDate date) {
        this.completedDates.add(date);
    }
}
