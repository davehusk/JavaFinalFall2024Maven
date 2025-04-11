package com.gym.admin;

import com.gym.user.*;
import com.gym.workout.*;
import com.gym.membership.MembershipService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminService {
    private final UserDAO userDAO = new UserDAO();
    private final WorkoutClassService workoutClassService = new WorkoutClassService();
    private final RoleManager roleManager = new RoleManager();
    private final MembershipService membershipService = new MembershipService();

    public void launchAdminDashboard(Scanner sc, User adminUser) throws SQLException {
        while (true) {
            System.out.println("\n=== ADMIN PANEL ===");
            System.out.println("1. View All Users");
            System.out.println("2. Promote/Demote User");
            System.out.println("3. Delete a User");
            System.out.println("4. Create Workout Class");
            System.out.println("5. Edit Workout Class");
            System.out.println("6. Delete Workout Class");
            System.out.println("7. View Class Roster");
            System.out.println("8. View System Stats");
            System.out.println("9. Back to Main Menu");
            System.out.print("Select option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> listAllUsers();
                case "2" -> roleManager.changeUserRole(sc);
                case "3" -> deleteUser(sc);
                case "4" -> workoutClassService.createClass(sc, adminUser);
                case "5" -> workoutClassService.editClass(sc);
                case "6" -> workoutClassService.deleteClass(sc);
                case "7" -> workoutClassService.viewClassRoster(sc);
                case "8" -> viewSystemStats();
                case "9" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void listAllUsers() throws SQLException {
        List<User> users = userDAO.findAll();
        System.out.println("\n--- USERS LIST ---");
        for (User u : users) {
            System.out.printf("ID: %d | Username: %s | Email: %s | Role: %s%n",
                u.getId(), u.getUsername(), u.getEmail(), u.getRole());
        }
    }

    private void deleteUser(Scanner sc) throws SQLException {
        System.out.print("Enter User ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        userDAO.deleteById(id);
        System.out.println("User deleted.");
    }

    private void viewSystemStats() throws SQLException {
        long total = userDAO.countAll();
        long admins = userDAO.countByRole("ADMIN");
        long trainers = userDAO.countByRole("TRAINER");
        long members = userDAO.countByRole("MEMBER");
        System.out.println("\n=== SYSTEM STATS ===");
        System.out.printf("Total Users: %d%nAdmins: %d | Trainers: %d | Members: %d%n",
            total, admins, trainers, members);
    }
}
