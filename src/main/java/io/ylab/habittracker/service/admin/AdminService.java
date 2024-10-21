package io.ylab.habittracker.service.admin;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.user.User;

import java.util.List;

public interface AdminService {
    List<User> getUsers();

    List<Habit> getUserHabits(long userId);

    void deleteUser(String email);

    User blockUser(String email);
}
