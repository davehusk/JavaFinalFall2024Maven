package core;

import exceptions.AuthenticationException;
import exceptions.AuthorizationException;
import exceptions.DatabaseException;
import exceptions.ValidationException;
import model.User;
import service.AuthService;
import util.Input;
import util.Log;
import util.Session;

public class MainMenu {
    private final AuthService authService;

    public MainMenu() {
        this.authService = ServiceFactory.getAuthService();
    }

    public void show() {
        while (true) {
            Input.clearScreen();
            displayMenu();
            handleUserChoice();
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Welcome to the Membership System ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
    }

    private void handleUserChoice() {
        int choice = -1;
        try {
            choice = Input.promptInt("Choose an option");
        } catch (ValidationException e) {
            Log.error("Invalid input: " + e.getMessage());
        }

        switch (choice) {
            case 1 -> handleRegister();
            case 2 -> handleLogin();
            case 0 -> exit();
            default -> Log.warn("Invalid option selected");
        }
    }

    private void handleRegister() {
        try {
            String name = Input.prompt("Enter your name");
            String email = Input.prompt("Enter your email");
            String password = Input.prompt("Enter your password");
            String role = Input.prompt("Select role (member/trainer/admin)");

            boolean success = authService.register(name, email, password, role);
            if (success) {
                Log.success("Registration successful! Please login.");
            }
        } catch (ValidationException e) {
            Log.error("Validation failed: " + e.getMessage());
        } catch (DatabaseException e) {
            Log.error("Database error: " + e.getMessage());
        } catch (Exception e) {
            Log.error("Unexpected error: " + e.getMessage());
        }

        try {
            Input.prompt("Press enter to continue...");
        } catch (ValidationException e) {
            Log.error("Validation failed: " + e.getMessage());
        }
    }

    private void handleLogin() {
        try {
            String email = Input.prompt("Email");
            String password = Input.prompt("Password");

            User user = authService.login(email, password);
            Session.login(user);
            Log.success("Login successful as " + user.getRole());

            new RoleRouterMenu().route();
        } catch (AuthenticationException e) {
            Log.error("Auth failed: " + e.getMessage());
        } catch (ValidationException e) {
            Log.error("Validation failed: " + e.getMessage());
        } catch (DatabaseException e) {
            Log.error("Database error: " + e.getMessage());
        } catch (AuthorizationException e) {
            Log.error("Unauthorized: " + e.getMessage());
        } catch (Exception e) {
            Log.error("Unexpected error: " + e.getMessage());
        }

        try {
            Input.prompt("Press enter to continue...");
        } catch (ValidationException e) {
            Log.error("Validation failed: " + e.getMessage());
        }
    }

    private void exit() {
        Log.info("Goodbye!");
        System.exit(0);
    }
}
