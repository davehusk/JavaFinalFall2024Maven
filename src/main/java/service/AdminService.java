package service;

import java.util.List;
import java.util.Optional;

import dao.PlanDAO;
import dao.UserDAO;
import exceptions.AuthenticationException;
import exceptions.AuthorizationException;
import exceptions.DatabaseException;
import exceptions.ValidationException;
import model.User;
import util.Input;
import util.Log;
import util.Session;

public class AdminService {
    private final UserDAO userDAO;
    private final PlanDAO planDAO;

    public AdminService(UserDAO userDAO, PlanDAO planDAO) {
        this.userDAO = userDAO;
        this.planDAO = planDAO;
    }

    public void viewAllUsers() throws DatabaseException {
        List<User> users = userDAO.findAll();
        if (users.isEmpty()) {
            Log.info("No users found");
            return;
        }

        System.out.println("\nAll Users:");
        System.out.println("----------");
        users.forEach(u -> System.out.printf(
            "ID: %d | %s | %s | Role: %s\n",
            u.getId(), u.getName(), u.getEmail(), u.getRole()
        ));
    }

    public void searchUsers(String keyword) {
        List<User> users = userDAO.search(keyword);
        if (users.isEmpty()) {
            System.out.println("❌ No matches found.");
        } else {
            System.out.println("🔍 Search Results:");
            users.forEach(u -> System.out.printf(
                "ID %d: %s <%s> | Role: %s\n",
                u.getId(), u.getName(), u.getEmail(), u.getRole()
            ));
        }
    }

    public void modifyUser(int userId)
            throws DatabaseException, ValidationException, AuthorizationException, AuthenticationException {

        if (!Session.currentUser().getRole().equals("admin")) {
            throw new AuthorizationException("Admin privileges required");
        }

        Optional<User> userOpt = userDAO.findById(userId);
        if (userOpt.isEmpty()) {
            throw new ValidationException("User not found");
        }

        User user = userOpt.get();
        System.out.println("\nEditing User: " + user.getName());

        String name = Input.prompt("New name [" + user.getName() + "]");
        String role = Input.prompt("New role [" + user.getRole() + "]");

        if (!name.isBlank()) user.setName(name);
        if (!role.isBlank()) user.setRole(role);

        if (!userDAO.update(user)) {
            throw new DatabaseException("Failed to update user");
        }

        Log.success("User updated successfully");
    }

    public void deleteUser(int id) {
        if (userDAO.delete(id)) {
            System.out.println("✅ User deleted.");
        } else {
            System.out.println("❌ Could not delete user.");
        }
    }

    public void viewAllMemberships() {
        planDAO.printMembershipJoin();
    }

    public void viewRevenue() {
        double sum = planDAO.calculateRevenue();
        System.out.printf("💰 Total revenue: $%.2f\n", sum);
    }
}
