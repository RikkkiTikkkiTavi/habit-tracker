package io.ylab.habittracker.menu;

import io.ylab.habittracker.habit.model.Frequency;
import io.ylab.habittracker.habit.model.Habit;
import io.ylab.habittracker.habit.service.HabitService;
import io.ylab.habittracker.history.service.HistoryService;
import io.ylab.habittracker.history.report.StatReport;
import io.ylab.habittracker.history.service.HistoryServiceImpl;
import io.ylab.habittracker.registration.password.Registration;
import io.ylab.habittracker.user.model.User;
import io.ylab.habittracker.user.service.UserService;
import io.ylab.habittracker.validate.NotFoundException;
import io.ylab.habittracker.validate.ValidationException;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserMenu {

    public static void userMenu(User user, UserService us, Registration reg, HabitService hs) {
        HistoryService his = new HistoryServiceImpl();
        Scanner sc = new Scanner(System.in);
        StatReport sr = new StatReport(his, hs);
        long userId = user.getId();

        while (true) {
            welcomeMenu(user);
            String command = sc.next();
            if (command.equals("0")) {
                break;
            }

            if (command.equals("9")) {
                us.deleteUser(user.getEmail());
                System.out.println("Аккаунт успешно удален!");
                break;
            }

            switch (command) {
                case "1" -> {
                    try {
                        System.out.println("Выберете привычку:");
                        for (Habit habit : hs.getHabits(userId)) {
                            System.out.println(habit.getId() + " " + habit);
                        }

                        System.out.println("Введите id:");

                        long num = sc.nextLong();
                        his.noteHabit(userId, num);
                        System.out.println("Привычка успешно отмечена");
                    } catch (InputMismatchException e) {
                        System.out.println("Введите число");
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    System.out.println("Ваш список привычек:");
                    List<Habit> habits = hs.getHabits(userId);
                    List<Habit> sortedList = getHabits(sc, habits);
                    for (Habit habit : sortedList) {
                        System.out.println(habit.getId() + " " + habit);
                    }
                }
                case "3" -> {
                    System.out.println("Введите id привычки");
                    try {
                        long habitId = sc.nextLong();
                        System.out.println(hs.getHabit(userId, habitId));
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Попробуйте другой id");
                    }
                }
                case "4" -> {
                    Habit habit = createHabit(hs, sc, userId);
                    System.out.println(habit);
                    System.out.println("Привычка успешно создана!");
                }
                case "5" -> {
                    Habit updateHabit = editHabit(sc, hs, userId);
                    System.out.println("Теперь привычка выглядит так");
                    System.out.println(updateHabit);
                }
                case "6" -> {
                    System.out.println("Введите id привычки");
                    long habitId = sc.nextLong();
                    hs.deleteHabit(userId, habitId);
                }
                case "7" -> {
                    System.out.println("Введите id привычки");
                    long habitId = sc.nextLong();
                    try {
                        sr.getReport(userId, habitId);
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "8" -> editUser(sc, user, us, reg);
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private static void editUser(Scanner sc, User user, UserService us, Registration reg) {
        String name = user.getName();
        String email = user.getEmail();

        while (true) {
            System.out.println("Что хотим редактировать");
            System.out.println("1 - Имя");
            System.out.println("2 - Email");
            System.out.println("3 - Пароль");
            System.out.println("0 - Назад");

            String command = sc.next();

            if (command.equals("0")) {
                break;
            }
            switch (command) {
                case "1" -> {
                    System.out.println("Старое имя " + user.getName());
                    System.out.println("Введите новое имя");
                    name = sc.next();
                    us.updateUser(user, name, email);
                    System.out.println("Имя изменено");
                }
                case "2" -> {
                    System.out.println("Старый email " + user.getEmail());
                    System.out.println("Введите новый email");
                    email = sc.next();
                    us.updateUser(user, name, email);
                    System.out.println("Email изменен");
                }
                case "3" -> {
                    System.out.println("Введите новый пароль");
                    String password = sc.next();
                    try {
                        reg.registration(user.getId(), password);
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Пароль успешно изменен");
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private static void welcomeMenu(User user) {
        System.out.println("-----------------------------------");
        System.out.println("Добро пожаловать " + user.getName());
        System.out.println("Выберете пункт");
        System.out.println("1 - Отметить выполнение привычки");
        System.out.println("2 - Список привычек");
        System.out.println("3 - Посмотреть привычку");
        System.out.println("4 - Создать привычку");
        System.out.println("5 - Редактировать привычку");
        System.out.println("6 - Удалить привычку");
        System.out.println("7 - Смотреть статистику");
        System.out.println("8 - Редактировать профиль");
        System.out.println("9 - Удалить аккаунт");
        System.out.println("0 - Выйти из аккаунта");
    }

    private static List<Habit> getHabits(Scanner sc, List<Habit> habits) {
        System.out.println("1 - Список привычек");
        System.out.println("2 - Отсортированный по дате создания список");
        System.out.println("3 - Отсортированный по статусу список");

        String command = sc.next();
        while (true) {
            switch (command) {
                case "1":
                    return habits;

                case "2":
                    return habits.stream().sorted(Comparator.comparing(Habit::getCreateTime))
                            .collect(Collectors.toList());
                case "3":
                    return habits.stream().sorted(Comparator.comparing(Habit::getFrequency))
                            .collect(Collectors.toList());
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }

    private static Habit createHabit(HabitService hs, Scanner sc, long userId) {
        System.out.println("Введите название привычки");
        String name = sc.next();
        System.out.println("Введите описание привычки");
        String description = sc.next();
        System.out.println("Выберете частотность привычки:");
        System.out.println("1 - Ежедневное повторение");
        System.out.println("2 - Еженедельное повторение");
        String command = sc.next();
        Frequency frequency = Frequency.EVERY_DAY;
        while (true) {
            if (command.equals("1")) {
                break;
            } else if (command.equals("2")) {
                frequency = Frequency.EVERY_WEEK;
                break;
            } else {
                System.out.println("Такой команды нет!");
            }
        }
        return hs.addHabit(new Habit(name, description, frequency, userId), userId);
    }

    private static Habit editHabit(Scanner sc, HabitService hs, long userId) {
        System.out.println("Введите id привычки");

        long habitId = sc.nextLong();

        Habit habit = hs.getHabit(userId, habitId);

        while (true) {
            System.out.println("Что хотим редактировать");
            System.out.println("1 - Имя");
            System.out.println("2 - Описание");
            System.out.println("3 - Частотность");
            System.out.println("0 - Назад");

            String command = sc.next();

            if (command.equals("0")) {
                break;
            }
            switch (command) {
                case "1":
                    System.out.println("Старое имя " + habit.getName());
                    System.out.println("Введите новое имя");
                    String name = sc.next();
                    habit.setName(name);
                    break;
                case "2":
                    System.out.println("Старое описание " + habit.getDescription());
                    System.out.println("Введите новое описание");
                    String description = sc.next();
                    habit.setDescription(description);
                    break;
                case "3":
                    if (habit.getFrequency().equals(Frequency.EVERY_DAY)) {
                        habit.setFrequency(Frequency.EVERY_WEEK);
                        System.out.println("Частотность изменена на Еженедельное повторение");
                    } else {
                        System.out.println("Частотность изменена на Ежедневное повторение");
                        habit.setFrequency(Frequency.EVERY_DAY);
                    }
                    break;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
        return habit;
    }
}
