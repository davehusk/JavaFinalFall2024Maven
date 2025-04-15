package menu;

import core.ConsoleMenu;
import core.ServiceFactory;
import service.MembershipService;
import service.WorkoutService;
import util.Input;
import util.Log;

public class MemberMenu {
    private final MembershipService membershipService;
    private final WorkoutService workoutService;

    public MemberMenu() {
        this.membershipService = ServiceFactory.getMembershipService();
        this.workoutService = ServiceFactory.getWorkoutService();
    }

    public void show() {
        ConsoleMenu menu = new ConsoleMenu("Member Dashboard");

        menu.addItem("View Current Membership", () -> {
            try {
                membershipService.viewCurrentMembership();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Purchase New Membership", () -> {
            try {
                if (!membershipService.purchaseMembership()) {
                    Log.info("Membership purchase cancelled");
                }
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View Available Classes", () -> {
            try {
                workoutService.viewAvailableClasses();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Attend a Class", () -> {
            try {
                int classId = Input.promptInt("Enter class ID to attend");
                if (classId == 0) return;
                workoutService.attendClass(classId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View Attendance History", () -> {
            try {
                workoutService.viewAttendanceHistory();
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
