package io.ylab.habittracker.service.user;

import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.repository.user.UserRepoManager;
import io.ylab.habittracker.repository.user.UserRepository;
import io.ylab.habittracker.validate.Validate;
import io.ylab.habittracker.validate.ValidationException;

public class UserServiceImpl implements UserService {

    private final UserRepository ur = UserRepoManager.getInstance();

    @Override
    public User createUser(User user) {
            Validate.checkEmail(user.getEmail());
            Validate.checkUserName(user.getName());
            if (ur.getUser(user.getEmail()) != null) {
                throw new ValidationException("Пользователь с таким email уже существует!");
            }
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
    public void deleteUser(String email) {
        ur.deleteUser(email);
    }
}
