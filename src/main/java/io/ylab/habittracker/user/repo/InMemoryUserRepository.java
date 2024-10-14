package io.ylab.habittracker.user.repo;

import io.ylab.habittracker.user.model.Status;
import io.ylab.habittracker.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    private Long id = 1L;

    @Override
    public User addUser(User user) {
        user.setId(id);
        user.setStatus(Status.AVAIlABLE);
        users.put(user.getEmail(), user);
        id++;
        return user;
    }

    @Override
    public User deleteUser(String email) {
        return users.remove(email);
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User getUser(String email) {
        return users.getOrDefault(email, null);
    }

    @Override
    public List<User> getUsers() {
        return users.values().stream().toList();
    }

}
