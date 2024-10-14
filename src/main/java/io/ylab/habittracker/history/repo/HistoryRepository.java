package io.ylab.habittracker.history.repo;

import io.ylab.habittracker.history.model.UserHistory;

public interface HistoryRepository {

    void addUserHistory(long userId, UserHistory uh);

    UserHistory getUserHistory(long userId);

}
