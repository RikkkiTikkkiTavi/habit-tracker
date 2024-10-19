package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.model.user.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);
    void deleteUser(String email);
    User updateUser(User user);
    User getUser(String email);

    List<User> getUsers();
}
