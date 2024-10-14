package io.ylab.habittracker.habit.repo;

public class HabitRepoManager {
    private HabitRepoManager() {
    }

    private static HabitRepository hr;

    public static HabitRepository getInstance() {
        if (hr == null) {
            hr = new InMemoryHabitRepository();
        }
        return hr;
    }
}
