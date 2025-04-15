package menu;

import core.ConsoleMenu;
import core.ServiceFactory;
import service.MembershipService;
import util.Input;
import util.Log;

public class PlanManagerMenu {
    private final MembershipService membershipService;

    public PlanManagerMenu() {
        this.membershipService = ServiceFactory.getMembershipService();
    }

    public void show() {
        ConsoleMenu menu = new ConsoleMenu("Plan Management");

        menu.addItem("View All Plans", () -> {
            try {
                membershipService.viewAllPlans();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Create Plan", () -> {
            try {
                if (!membershipService.createPlan()) {
                    Log.info("Plan creation cancelled");
                }
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Edit Plan", () -> {
            try {
                int planId = Input.promptInt("Enter plan ID to edit");
                if (planId == 0) return;
                membershipService.editPlan(planId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Delete Plan", () -> {
            try {
                int planId = Input.promptInt("Enter plan ID to delete");
                if (planId == 0) return;
                membershipService.deletePlan(planId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
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
