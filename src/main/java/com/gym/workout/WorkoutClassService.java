package com.gym.workout;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.gym.user.User;

public class WorkoutClassService {
    private final WorkoutClassDAO classDAO = new WorkoutClassDAO();

    public void createClass(Scanner sc, User trainer) throws SQLException {
        System.out.print("Enter class name: ");
        String name = sc.nextLine();
        System.out.print("Enter date-time (YYYY-MM-DDTHH:MM): ");
        String input = sc.nextLine();

        try {
            LocalDateTime dateTime = LocalDateTime.parse(input);
            WorkoutClass wc = new WorkoutClass(name, trainer.getId(), dateTime);
            classDAO.create(wc);
            System.out.println("Class created successfully.");
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Invalid date format.");
        } catch (SQLException e) {
            System.out.println("Database error occurred.");
        }
    }

    public void listClassesByTrainer(int trainerId) throws SQLException {
        List<WorkoutClass> classes = classDAO.findByTrainerId(trainerId);
        if (classes.isEmpty()) {
            System.out.println("No classes found.");
        } else {
            for (WorkoutClass c : classes) {
                System.out.printf("Class ID: %d | Name: %s | Time: %s%n",
                    c.getId(), c.getClassName(), c.getSchedule());
            }
        }
    }

    public void registerForClass(Scanner sc, User member) throws SQLException {
        List<WorkoutClass> classes = classDAO.findAll();
        if (classes.isEmpty()) {
            System.out.println("No available classes.");
            return;
        }

        System.out.println("Available Classes:");
        for (WorkoutClass c : classes) {
            System.out.printf("ID: %d | Name: %s | Time: %s%n", c.getId(), c.getClassName(), c.getSchedule());
        }

        System.out.print("Enter class ID to register: ");
        int classId = Integer.parseInt(sc.nextLine());

        ClassRegistrationDAO registrationDAO = new ClassRegistrationDAO();
        registrationDAO.register(member.getId(), classId);
        System.out.println("Registered successfully!");
    }

    public void editClass(Scanner sc) throws SQLException {
        System.out.print("Enter class ID to edit: ");
        int classId = Integer.parseInt(sc.nextLine());
        System.out.print("New class name: ");
        String name = sc.nextLine();
        System.out.print("New date-time (YYYY-MM-DDTHH:MM): ");
        String dateTimeStr = sc.nextLine();

        LocalDateTime newSchedule = LocalDateTime.parse(dateTimeStr);
        classDAO.update(classId, name, newSchedule);
        System.out.println("Class updated.");
    }

    public void deleteClass(Scanner sc) throws SQLException {
        System.out.print("Enter class ID to delete: ");
        int classId = Integer.parseInt(sc.nextLine());
        classDAO.delete(classId);
        System.out.println("Class deleted.");
    }

    public void viewClassRoster(Scanner sc) throws SQLException {
        System.out.print("Enter class ID: ");
        int classId = Integer.parseInt(sc.nextLine());
        classDAO.printClassRoster(classId);
    }
}
