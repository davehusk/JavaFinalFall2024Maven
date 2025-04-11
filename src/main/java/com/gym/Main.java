package com.gym;

import java.sql.SQLException;
import java.util.Scanner;

import com.gym.admin.AdminService;
import com.gym.checkin.CheckinService;
import com.gym.membership.MembershipService;
import com.gym.payment.PaymentService;
import com.gym.user.User;
import com.gym.user.UserService;
import com.gym.workout.WorkoutClassService;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final WorkoutClassService workoutClassService = new WorkoutClassService();
    private static final MembershipService membershipService = new MembershipService();
    private static final CheckinService checkinService = new CheckinService();
    private static final PaymentService paymentService = new PaymentService();

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
                    case "1" -> userService.register(sc);
                    case "2" -> {
                        User user = userService.login(sc);
                        if (user != null) routeByRole(user);
                    }
                    case "3" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void routeByRole(User user) throws SQLException {
        switch (user.getRole()) {
            case "ADMIN" -> new AdminService().launchAdminDashboard(sc, user);
            case "TRAINER" -> trainerMenu(user);
            case "MEMBER" -> memberMenu(user);
            default -> System.out.println("Unknown role: " + user.getRole());
        }
    }

    private static void trainerMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n=== Trainer Dashboard ===");
            System.out.println("1. View My Classes");
            System.out.println("2. View Class Rosters");
            System.out.println("3. View Daily Check-ins");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            String option = sc.nextLine();

            switch (option) {
                case "1" -> workoutClassService.listClassesByTrainer(user.getId());
                case "2" -> workoutClassService.viewClassRoster(sc);
                case "3" -> checkinService.viewCheckinsToday();
                case "4" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void memberMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n=== Member Dashboard ===");
            System.out.println("1. Register for a Class");
            System.out.println("2. View My Membership");
            System.out.println("3. Renew Membership");
            System.out.println("4. Check In Today");
            System.out.println("5. View Check-in History");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            String option = sc.nextLine();

            switch (option) {
                case "1" -> workoutClassService.registerForClass(sc, user);
                case "2" -> membershipService.viewMembership(user);
                case "3" -> paymentService.renewMembership(user);
                case "4" -> checkinService.recordCheckin(user);
                case "5" -> checkinService.viewCheckinHistory(user);
                case "6" -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
