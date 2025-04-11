package com.gym;

import com.gym.user.User;
import com.gym.user.UserService;
import com.gym.membership.MembershipService;
import com.gym.workout.WorkoutClassService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final MembershipService membershipService = new MembershipService();
    private static final WorkoutClassService workoutClassService = new WorkoutClassService();

    public static void main(String[] args) {
        System.out.println("=== Welcome to Gym Management System ===");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select option: ");
            String option = sc.nextLine();

            try {
                switch (option) {
                    case "1":
                        userService.register(sc);
                        break;
                    case "2":
                        User user = userService.login(sc);
                        if (user != null) {
                            routeByRole(user);
                        }
                        break;
                    case "3":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void routeByRole(User user) throws SQLException {
        switch (user.getRole()) {
            case "ADMIN":
                adminMenu(user);
                break;
            case "TRAINER":
                trainerMenu(user);
                break;
            case "MEMBER":
                memberMenu(user);
                break;
            default:
                System.out.println("Unknown role: " + user.getRole());
        }
    }

    private static void adminMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n=== Admin Dashboard ===");
            System.out.println("1. View All Users");
            System.out.println("2. Create Workout Class");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    userService.listUsers();
                    break;
                case "2":
                    workoutClassService.createClass(sc, user);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void trainerMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n=== Trainer Dashboard ===");
            System.out.println("1. View My Classes");
            System.out.println("2. Logout");
            System.out.print("Choose: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    workoutClassService.listClassesByTrainer(user.getId());
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void memberMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n=== Member Dashboard ===");
            System.out.println("1. Register for a Class");
            System.out.println("2. View My Membership");
            System.out.println("3. Logout");
            System.out.print("Choose: ");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    workoutClassService.registerForClass(sc, user);
                    break;
                case "2":
                    membershipService.viewMembership(user);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
