package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl test = new UserServiceImpl();
        test.createUsersTable();
        test.saveUser("John", "Doe", (byte) 22);
        System.out.println("User with name - John Doe added to the database.");
        test.saveUser("Jane", "Doe", (byte) 24);
        System.out.println("User with name - Jane Doe added to the database.");
        test.saveUser("Gildong", "Hong", (byte) 55);
        System.out.println("User with name - Gildong Hong added to the database.");
        test.saveUser("Nick", "Martin", (byte) 40);
        System.out.println("User with name - Nick Martin added to the database.");
        List<User> users = test.getAllUsers();
        for(User user : users){
            System.out.println(user.toString());
        }
        test.cleanUsersTable();
        test.dropUsersTable();
    }
}
