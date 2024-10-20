package io.ylab.habittracker.service.user;

import io.ylab.habittracker.model.user.User;

public interface UserService {
    User createUser(User user);

    User getUser(String email);

    User updateUser(User user);

    void deleteUser(String email);
}
