package io.ylab.habittracker.service.admin;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.repository.habit.HabitRepoManager;
import io.ylab.habittracker.repository.habit.HabitRepository;
import io.ylab.habittracker.model.user.Status;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.repository.user.UserRepoManager;
import io.ylab.habittracker.repository.user.UserRepository;
import io.ylab.habittracker.validate.ValidationException;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    UserRepository us = UserRepoManager.getInstance();
    HabitRepository hr = HabitRepoManager.getInstance();

    @Override
    public List<User> getUsers() {
        return us.getUsers();
    }

    @Override
    public List<Habit> getUserHabits(long userId) {
        return hr.getUserHabits(userId);
    }

    @Override
    public void deleteUser(String email) {
        us.deleteUser(email);
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
