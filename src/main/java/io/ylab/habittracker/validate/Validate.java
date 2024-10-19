package io.ylab.habittracker.validate;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.user.User;

import java.time.LocalDate;

public class Validate {
    public static void checkEmail(String email) {
        if (!email.contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
    }

    public static void checkPassword(String password) {
        if (password.length() < 6) {
            throw new ValidationException("Пароль должен содержать не менее 6 символов");
        }

        if (password.contains(" ")) {
            throw new ValidationException("Пароль не должен содержать пустых символов");
        }

        if (password.length() >= 12) {
            throw new ValidationException("Пароль должен содержать не более 12 символов");
        }
    }

    public static void checkUser(User user) {
        if (user == null) {
            throw new NotFoundException("Такого пользователя не существует!");
        }
    }

    public static void checkUserName(String name) {
        if (name.length() > 20 || name.isEmpty()) {
            throw new ValidationException("Имя не может быть пустым и должно содержать не более 20 символов");
        }
    }

    public static void checkHabit(Habit habit) {
        if (habit == null) {
            throw new NotFoundException("Такой привычки не существует");
        }

        String name = habit.getName();
        String description = habit.getDescription();

        if (name.length() > 20 || name.isEmpty()) {
            throw new ValidationException("Имя не может быть пустым и должно содержать не более 20 символов");
        }

        if (description.length() > 200 || description.isEmpty()) {
            throw new ValidationException("Описание не может быть пустым и должно содержать не более 200 символов");
        }
    }

    public static void checkPeriod(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.of(2000, 1, 1)) || start.isAfter(end)) {
            throw new ValidationException("Указан неверный период");
        }
    }
}
