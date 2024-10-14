package io.ylab.habittracker.menu;


import io.ylab.habittracker.habit.service.HabitService;
import io.ylab.habittracker.habit.service.HabitServiceImpl;
import io.ylab.habittracker.registration.password.Registration;
import io.ylab.habittracker.user.model.Status;
import io.ylab.habittracker.user.model.User;
import io.ylab.habittracker.user.service.UserService;
import io.ylab.habittracker.user.service.UserServiceImpl;
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
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    registration(reg, user, us, hs);
                    break;

                case "2":
                    try {
                        User newUser = createUser(sc, us);
                        System.out.println("Введите пароль");
                        String password = sc.next();
                        reg.registration(newUser.getId(), password);
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

    private static User createUser(Scanner sc, UserService us) {
        System.out.println("Как вас зовут?");
        String name = sc.next();
        System.out.println("Введите адрес электронной почты");
        String email = sc.next();
        return us.createUser(name, email);
    }

    private static void registration(Registration reg, User user, UserService us, HabitService hs) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите пароль");
            String password = sc.next();
            long userId = user.getId();

            if (reg.authentication(userId, password)) {
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
