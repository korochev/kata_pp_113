package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;



public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 26);
        userService.saveUser("Vasily", "Ivanov", (byte) 16);
        userService.saveUser("Petr", "Petrovsky", (byte) 40);
        userService.saveUser("Vitya", "Pupkin", (byte) 32);
        userService.getAllUsers().stream().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        //userService.removeUserById(1);
    }
}
