package menu;

import core.ConsoleMenu;
import core.ServiceFactory;
import service.AdminService;
import service.MembershipService;
import util.Input;
import util.Log;

public class AdminMenu {
    private final AdminService adminService;
    private final MembershipService membershipService;

    public AdminMenu() {
        this.adminService = ServiceFactory.getAdminService();
        this.membershipService = ServiceFactory.getMembershipService();
    }

    public void show() {
        ConsoleMenu menu = new ConsoleMenu("Admin Dashboard");

        menu.addItem("View All Users", () -> {
            try {
                adminService.viewAllUsers();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Search User", () -> {
            try {
                String keyword = Input.prompt("Enter search keyword");
                if (keyword != null) {
                    adminService.searchUsers(keyword);
                }
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Modify User", () -> {
            try {
                int userId = Input.promptInt("Enter user ID to modify");
                adminService.modifyUser(userId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Delete User", () -> {
            try {
                int userId = Input.promptInt("Enter user ID to delete");
                adminService.deleteUser(userId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View All Memberships", () -> {
            try {
                adminService.viewAllMemberships();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View Total Revenue", () -> {
            try {
                adminService.viewRevenue();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Manage Membership Plans", () -> {
            try {
                new PlanManagerMenu().show();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
        });

        menu.show();
    }

    private void waitPrompt() {
        try {
            Input.prompt("Press enter to continue...");
        } catch (Exception ignored) {
        }
    }
}
