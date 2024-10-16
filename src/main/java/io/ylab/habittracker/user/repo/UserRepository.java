package io.ylab.habittracker.user.repo;

import io.ylab.habittracker.user.model.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);
    User deleteUser(String email);
    User updateUser(User user);
    User getUser(String email);

    List<User> getUsers();
}
