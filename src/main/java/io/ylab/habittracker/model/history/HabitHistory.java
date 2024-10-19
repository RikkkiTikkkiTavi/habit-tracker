package io.ylab.habittracker.model.history;

import java.time.LocalDate;

public class HabitHistory {
    private long habitId;
    private long userId;
    private LocalDate date;
    private int streak;

    public HabitHistory(long habitId, long userId, LocalDate date) {
        this.habitId = habitId;
        this.userId = userId;
        this.date = date;
        this.streak = 1;
    }

    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HabitHistory{");
        sb.append("habitId=").append(habitId);
        sb.append(", userId=").append(userId);
        sb.append(", date=").append(date);
        sb.append(", streak=").append(streak);
        sb.append('}');
        return sb.toString();
    }
}
