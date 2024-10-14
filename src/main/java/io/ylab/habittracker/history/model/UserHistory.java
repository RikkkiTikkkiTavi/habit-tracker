package io.ylab.habittracker.history.model;

import java.util.HashMap;
import java.util.Map;

public class UserHistory {
    private final Map<Long, HabitHistory> completedHabits;

    public UserHistory() {
        this.completedHabits = new HashMap<>();
    }

    public Map<Long, HabitHistory> getCompletedHabits() {
        return completedHabits;
    }
}
