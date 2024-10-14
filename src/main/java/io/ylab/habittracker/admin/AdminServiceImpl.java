package io.ylab.habittracker.admin;

import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.habit.repo.HabitRepoManager;
import io.ylab.habittracker.habit.repo.HabitRepository;
import io.ylab.habittracker.user.model.Status;
import io.ylab.habittracker.user.model.User;
import io.ylab.habittracker.user.repo.UserRepoManager;
import io.ylab.habittracker.user.repo.UserRepository;
import io.ylab.habittracker.validate.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminServiceImpl implements AdminService {

    UserRepository us = UserRepoManager.getInstance();
    HabitRepository hr = HabitRepoManager.getInstance();

    @Override
    public List<User> getUsers() {
        return us.getUsers();
    }

    @Override
    public List<Habit> getUserHabits(long userId) {
        Map<Long, Habit> habits = hr.getUserHabits(userId);
        if (habits == null) {
            throw new ValidationException("У пользователя нет привычек");
        }
        return new ArrayList<>(hr.getUserHabits(userId).values());
    }

    @Override
    public User deleteUser(String email) {
        return us.deleteUser(email);
    }

    @Override
    public User blockUser(String email) {
        User user = us.getUser(email);
        if (user != null) {
            user.setStatus(Status.BLOCKED);
            return user;
        } else {
            throw new ValidationException("Такого пользователя не существует!");
        }
    }
}
