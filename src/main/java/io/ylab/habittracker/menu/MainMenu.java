package io.ylab.habittracker.menu;


import io.ylab.habittracker.model.user.Status;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.service.habit.HabitService;
import io.ylab.habittracker.service.habit.HabitServiceImpl;
import io.ylab.habittracker.service.registration.Registration;
import io.ylab.habittracker.service.user.UserService;
import io.ylab.habittracker.service.user.UserServiceImpl;
import io.ylab.habittracker.validate.ValidationException;

import java.util.Scanner;

public class MainMenu {

    public static void startMenu() {
        Scanner sc = new Scanner(System.in);
        UserService us = new UserServiceImpl();
        Registration reg = new Registration();
        HabitService hs = new HabitServiceImpl();

        while (true) {
            welcomeMenu();
            String command = sc.next();
            if (command.equals("0")) {
                break;
            }
            switch (command) {
                case "1":
                    User user;
                    System.out.println("Введите email");
                    String email = sc.next();

                    if (email.equals("admin@admin")) {
                        AdminMenu.adminMenu();
                        break;
                    }

                    try {
                        user = us.getUser(email);
                        authentication(reg, user, us, hs);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;

                case "2":
                    try {
                        User newUser = createUser(sc, us, reg);
                        System.out.println(us.createUser(newUser));
                        System.out.println("Аккаунт успешно создан");
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Такой команды не существует");
            }
        }
    }

    private static void welcomeMenu() {
        System.out.println("____________________________________________________________________");
        System.out.println("Добро пожаловать в трекер привычек! Пожалуйста выберете нужный пункт!");
        System.out.println("1 - Войти в трекер");
        System.out.println("2 - Зарегистрироваться");
        System.out.println("0 - Завершить работу приложения");
    }

    private static User createUser(Scanner sc, UserService us, Registration reg) {
        System.out.println("Как вас зовут?");
        String name = sc.next();
        System.out.println("Введите адрес электронной почты");
        String email = sc.next();
        System.out.println("Введите пароль");
        String password = sc.next();
        User newUser = new User(name, email);
        reg.registration(newUser, password);
        return newUser;
    }

    private static void authentication(Registration reg, User user, UserService us, HabitService hs) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите пароль");
            String password = sc.next();

            if (reg.authentication(user, password)) {
                if (user.getStatus().equals(Status.BLOCKED)) {
                    System.out.println("Соболезнуем, ваш аккаунт заблокирован");
                } else {
                    UserMenu.userMenu(user, us, reg, hs);
                    break;
                }

            } else {
                System.out.println("Неверный логин или пароль. Повторите попытку!");
                break;
            }
        }
    }
}
