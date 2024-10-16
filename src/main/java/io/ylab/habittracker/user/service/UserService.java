package io.ylab.habittracker.user.service;

import io.ylab.habittracker.user.model.User;

public interface UserService {
    User createUser(String name, String email);
    User getUser(String email);

    User updateUser(User user, String name, String email);

    User deleteUser(String email);
}
