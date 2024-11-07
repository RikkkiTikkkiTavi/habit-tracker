package io.ylab.habittracker.menu;

import io.ylab.habittracker.model.habit.Habit;
import io.ylab.habittracker.model.user.User;
import io.ylab.habittracker.service.admin.AdminService;
import io.ylab.habittracker.service.admin.AdminServiceImpl;
import io.ylab.habittracker.validate.ValidationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void adminMenu() {
        AdminService as = new AdminServiceImpl();
        Scanner sc = new Scanner(System.in);
        while (true) {
            welcomeMenu();
            String command = sc.next();
            if (command.equals("0")) {
                break;
            }
            try {
                switch (command) {
                    case "1":
                        List<User> users = as.getUsers();
                        for (User user : users) {
                            System.out.println(user);
                        }
                        break;
                    case "2":
                        try {
                            System.out.println("Введите id пользователя");
                            long userId = sc.nextLong();
                            List<Habit> habits = as.getUserHabits(userId);
                            for (Habit habit : habits) {
                                System.out.println(habit);
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("id пользователя целочисленное число");
                        }
                    case "3":
                        System.out.println("Введите email пользователя");
                        String email = sc.next();
                        System.out.println("Пользователь" + as.blockUser(email) + " заблокирован");
                        break;
                    case "4":
                        System.out.println("Введите email пользователя");
                        String ema = sc.next();
                        as.deleteUser(ema);
                        System.out.println("Пользователь удален");
                        break;

                    default:
                        System.out.println("Неопознанная команда");
                        break;
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }

    }
        private static void welcomeMenu() {
            System.out.println("-----------------------------------");
            System.out.println("Добро пожаловать");
            System.out.println("Выберете пункт");
            System.out.println("1 - Получить список пользователей");
            System.out.println("2 - Получить список привычек пользователя");
            System.out.println("3 - Заблокировать пользователя");
            System.out.println("4 - Удалить пользователя");
            System.out.println("0 - Выйти из аккаунта");
        }
}
