package com.gym.admin;

import java.sql.SQLException;
import java.util.Scanner;

import com.gym.user.User;
import com.gym.user.UserDAO;

public class RoleManager {
    private final UserDAO userDAO = new UserDAO();

    public void changeUserRole(Scanner sc) throws SQLException {
        System.out.print("Enter username: ");
        String username = sc.nextLine();

        User user = userDAO.findByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Current role: " + user.getRole());
        System.out.print("Enter new role (ADMIN/TRAINER/MEMBER): ");
        String role = sc.nextLine().toUpperCase();

        if (!role.matches("ADMIN|TRAINER|MEMBER")) {
            System.out.println("Invalid role.");
            return;
        }

        userDAO.updateRole(user.getId(), role);
        System.out.println("Role updated.");
    }
}
