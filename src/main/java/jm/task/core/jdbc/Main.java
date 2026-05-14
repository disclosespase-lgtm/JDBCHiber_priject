package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();

        //создали табле
        userService.createUsersTable();

        //добавили человеков 5шт
        userService.saveUser("Сергей", "Федоренко",  (byte) 20);
        userService.saveUser("Матвей", "Пипколипко",  (byte) 13);
        userService.saveUser("Иван", "Кокушкин",  (byte) 62);
        userService.saveUser("Ригина", "Наморозильникова",  (byte) 43);
        userService.saveUser("Клавдия", "Муравешкина",  (byte) 30);

        //Вывели лист в консольку
        List<User> listUsers = userService.getAllUsers();
        for(User user:listUsers){
            System.out.println(user);
        }

        //Очистили табле
        userService.cleanUsersTable();
        //Удалили
        userService.dropUsersTable();

    }
}
