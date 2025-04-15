package menu;

import core.ConsoleMenu;
import core.ServiceFactory;
import service.WorkoutService;
import util.Input;
import util.Log;

public class TrainerMenu {
    private final WorkoutService workoutService;

    public TrainerMenu() {
        this.workoutService = ServiceFactory.getWorkoutService();
    }

    public void show() {
        ConsoleMenu menu = new ConsoleMenu("Trainer Dashboard");

        menu.addItem("Create Workout Class", () -> {
            try {
                if (!workoutService.createClass()) {
                    Log.info("Class creation cancelled");
                }
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View My Classes", () -> {
            try {
                workoutService.viewMyClasses();
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("Delete Class", () -> {
            try {
                int classId = Input.promptInt("Enter class ID to delete");
                if (classId == 0) return;
                workoutService.deleteClass(classId);
            } catch (Exception e) {
                Log.error(e.getMessage());
            }
            waitPrompt();
        });

        menu.addItem("View Class Attendance", () -> {
            try {
                int classId = Input.promptInt("Enter class ID to view attendance");
                if (classId == 0) return;
                workoutService.viewClassAttendance(classId);
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
