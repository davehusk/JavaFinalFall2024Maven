package core;

import dao.*;
import service.*;

public class ServiceFactory {
    private static final UserDAO userDAO = new UserDAO();
    private static final PlanDAO planDAO = new PlanDAO();
    private static final MembershipDAO membershipDAO = new MembershipDAO();
    private static final WorkoutClassDAO workoutClassDAO = new WorkoutClassDAO();
    private static final AttendanceDAO attendanceDAO = new AttendanceDAO();

    private static final AuthService authService = new AuthService(userDAO);
    private static final AdminService adminService = new AdminService(userDAO, planDAO);
    private static final MembershipService membershipService = new MembershipService(membershipDAO, planDAO);
    private static final WorkoutService workoutService = new WorkoutService(workoutClassDAO, attendanceDAO, membershipDAO);

    public static AuthService getAuthService() {
        return authService;
    }

    public static AdminService getAdminService() {
        return adminService;
    }

    public static MembershipService getMembershipService() {
        return membershipService;
    }

    public static WorkoutService getWorkoutService() {
        return workoutService;
    }
}
