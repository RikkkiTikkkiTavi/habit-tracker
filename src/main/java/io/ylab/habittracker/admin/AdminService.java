package io.ylab.habittracker.admin;

import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.user.model.User;

import java.util.List;

public interface AdminService {
    List<User> getUsers();

    List<Habit> getUserHabits(long userId);

    User deleteUser(String email);

    User blockUser(String email);
}
