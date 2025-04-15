package service;

import java.util.List;

import dao.AttendanceDAO;
import dao.MembershipDAO;
import dao.WorkoutClassDAO;
import exceptions.AuthenticationException;
import exceptions.DatabaseException;
import exceptions.ServiceException;
import exceptions.ValidationException;
import model.WorkoutClass;
import util.Input;
import util.Log;
import util.Session;
import util.ValidationUtil;

public class WorkoutService {
    private final WorkoutClassDAO classDAO;
    private final AttendanceDAO attendanceDAO;
    private final MembershipDAO membershipDAO;

    public WorkoutService(WorkoutClassDAO classDAO, AttendanceDAO attendanceDAO, MembershipDAO membershipDAO) {
        this.classDAO = classDAO;
        this.attendanceDAO = attendanceDAO;
        this.membershipDAO = membershipDAO;
    }

    public void viewAvailableClasses() throws ServiceException {
        try {
            List<WorkoutClass> classes = classDAO.getAll();
            if (classes.isEmpty()) {
                Log.info("No classes available at this time");
                return;
            }

            System.out.println("\nAvailable Classes:");
            System.out.println("-----------------");
            classes.forEach(c -> System.out.printf(
                "ID: %d | %s | Schedule: %s\n",
                c.getId(), c.getName(), c.getSchedule()
            ));
        } catch (DatabaseException e) {
            throw new ServiceException("Failed to retrieve classes", e);
        }
    }

    public void attendClass(int classId) throws ServiceException {
        try {
            int userId = Session.currentUser().getId();

            if (membershipDAO.getActiveMembership(userId).isEmpty()) {
                throw new ValidationException("Active membership required to attend classes");
            }

            if (!attendanceDAO.logAttendance(userId, classId)) {
                throw new DatabaseException("Failed to record attendance");
            }

            Log.success("Attendance recorded successfully");
        } catch (Exception e) {
            throw new ServiceException("Failed to attend class: " + e.getMessage(), e);
        }
    }

    
    public void viewAttendanceHistory() throws ServiceException, DatabaseException, AuthenticationException {
        try {
            List<String> history = attendanceDAO.getHistory(Session.currentUser().getId());
            if (history.isEmpty()) {
                System.out.println("❌ No attendance history.");
                return;
            }

            System.out.println("📖 Class Attendance History:");
            history.forEach(System.out::println);
        } catch (DatabaseException e) {
            Log.error("Failed to get attendance history: " + e.getMessage());
            throw e;
        } catch (AuthenticationException e) {
            Log.error("Authentication error: " + e.getMessage());
            throw e;
        }
    }

    public boolean createClass() throws ServiceException, AuthenticationException {
        try {
            String name = Input.prompt("Class name");
            ValidationUtil.validateNotBlank(name, "Class name");

            String desc = Input.prompt("Description");
            ValidationUtil.validateNotBlank(desc, "Description");

            String sched = Input.prompt("Schedule (e.g. Mon/Wed 5pm)");
            ValidationUtil.validateNotBlank(sched, "Schedule");

            WorkoutClass wc = new WorkoutClass();
            wc.setName(name);
            wc.setDescription(desc);
            wc.setSchedule(sched);
            wc.setTrainerId(Session.currentUser().getId());

            boolean success = classDAO.create(wc);
            if (success) {
                Log.success("Class created successfully!");
            }
            return success;
        } catch (ValidationException | DatabaseException e) {
            throw new ServiceException("Failed to create class: " + e.getMessage(), e);
        }
    }

    public void viewMyClasses() throws ServiceException, AuthenticationException {
        try {
            int trainerId = Session.currentUser().getId();
            List<WorkoutClass> classes = classDAO.getByTrainer(trainerId);

            if (classes.isEmpty()) {
                Log.info("You haven't created any classes yet");
                return;
            }

            System.out.println("\nYour Classes:");
            System.out.println("------------");
            classes.forEach(c -> System.out.printf(
                "ID: %d | %s | %s\n",
                c.getId(), c.getName(), c.getSchedule()
            ));
        } catch (DatabaseException e) {
            throw new ServiceException("Failed to retrieve your classes", e);
        }
    }

    public void deleteClass(int classId) throws AuthenticationException {
        try {
            boolean success = classDAO.delete(classId, Session.currentUser().getId());
            System.out.println(success ? "✅ Class deleted." : "❌ Class not deleted or not yours.");
        } catch (DatabaseException e) {
            Log.error("Error deleting class: " + e.getMessage());
        }
    }

    public void viewClassAttendance(int classId) {
        try {
            var attendees = attendanceDAO.getClassAttendance(classId);
            if (attendees.isEmpty()) {
                System.out.println("📭 No one has attended this class yet.");
                return;
            }
            System.out.println("🧍 Attendees:");
            attendees.forEach(name -> System.out.println("- " + name));
        } catch (DatabaseException e) {
            Log.error("Error viewing class attendance: " + e.getMessage());
        }
    }
}
