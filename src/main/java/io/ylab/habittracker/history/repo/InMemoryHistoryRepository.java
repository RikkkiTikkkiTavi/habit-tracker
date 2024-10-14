package io.ylab.habittracker.history.repo;

import io.ylab.habittracker.history.model.UserHistory;

import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryRepository implements HistoryRepository {
    private final Map<Long, UserHistory> history = new HashMap<>();

    @Override
    public void addUserHistory(long userId, UserHistory uh) {
        history.put(userId, uh);
    }

    @Override
    public UserHistory getUserHistory(long userId) {
        return history.getOrDefault(userId, null);
    }
}

