package io.ylab.habittracker.repository.user;

import io.ylab.habittracker.model.user.Status;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.repository.habit.HabitRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс позволяющий сохранять привычки в оперативной памяти
 * @see UserRepository - реализуемый интерфейс
 * @autor Константин Щеглов
 */
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    private Long id = 1L;

    @Override
    public User addUser(User user) {
        user.setId(id);
        user.setStatus(Status.AVAILABLE);
        users.put(user.getEmail(), user);
        id++;
        return user;
    }



        @Override
    public void deleteUser(String email) {

    }

    //    @Override
//    public User deleteUser(String email) {
//        return users.remove(email);
//    }

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
