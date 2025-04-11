package com.gym.user;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public void register(Scanner sc) throws SQLException {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Role (ADMIN/TRAINER/MEMBER): ");
        String role = sc.nextLine().toUpperCase();

        if (!role.matches("ADMIN|TRAINER|MEMBER")) {
            System.out.println("Invalid role.");
            return;
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, email, hashed, role);
        userDAO.insert(user);
        System.out.println("Registration complete!");
    }

    public User login(Scanner sc) throws SQLException {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userDAO.findByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Invalid username or password.");
            return null;
        }

        System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
        return user;
    }

    public void listUsers() throws SQLException {
        List<User> users = userDAO.findAll();
        for (User u : users) {
            System.out.printf("ID: %d | Username: %s | Email: %s | Role: %s%n", 
                u.getId(), u.getUsername(), u.getEmail(), u.getRole());
        }
    }
}
