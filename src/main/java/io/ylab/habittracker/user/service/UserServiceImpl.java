package io.ylab.habittracker.user.service;

import io.ylab.habittracker.user.model.User;
import io.ylab.habittracker.user.repo.UserRepoManager;
import io.ylab.habittracker.user.repo.UserRepository;
import io.ylab.habittracker.validate.Validate;
import io.ylab.habittracker.validate.ValidationException;

public class UserServiceImpl implements UserService {

    private final UserRepository ur = UserRepoManager.getInstance();

    @Override
    public User createUser(String name, String email) {
            Validate.checkEmail(email);
            Validate.checkUserName(name);
            if (ur.getUser(email) != null) {
                throw new ValidationException("Пользователь с таким email уже существует!");
            }
            User user = new User(name, email);
            user = ur.addUser(user);
        return user;
    }

    @Override
    public User getUser(String email) {
        Validate.checkEmail(email);
        User user = ur.getUser(email);
        Validate.checkUser(user);
        return user;
    }

    @Override
    public User updateUser(User user, String name, String email) {
        Validate.checkUserName(name);
        Validate.checkEmail(email);
        if (ur.getUser(email) != null) {
            throw new ValidationException("Email занят!");
        }
        return ur.updateUser(user);
    }

    @Override
    public User deleteUser(String email) {
        return ur.deleteUser(email);
    }
}
