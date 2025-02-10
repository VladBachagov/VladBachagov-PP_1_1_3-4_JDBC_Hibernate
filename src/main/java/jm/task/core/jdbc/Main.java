package jm.task.core.jdbc;

import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // 1. Создание таблицы пользователей
        userService.createUsersTable();

        // 2. Добавление 4 пользователей
        userService.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Петр", "Петров", (byte) 30);
        userService.saveUser("Сергей", "Сергеев", (byte) 35);
        userService.saveUser("Анна", "Антонова", (byte) 28);

        // 3. Получение всех пользователей и вывод
        List<User> users = userService.getAllUsers();
        System.out.println("Список пользователей в базе:");
        for (User user : users) {
            System.out.println(user);
        }

    // 4.Очистка таблицы пользователей
    userService.cleanUsersTable();

    // 5. Удаление таблицы пользователей
    userService.dropUsersTable();
    }
}