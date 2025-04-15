package util;

import dao.*;
import exceptions.DatabaseException;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class Seeder {

    public static void seed() throws DatabaseException {
        Log.info("🌱 Starting database seed...");

        TransactionManager.execute(conn -> {
            try {
                clearData(conn);

                // DAOs
                UserDAO userDAO = new UserDAO();
                PlanDAO planDAO = new PlanDAO();
                MembershipDAO membershipDAO = new MembershipDAO();
                WorkoutClassDAO workoutClassDAO = new WorkoutClassDAO();
                AttendanceDAO attendanceDAO = new AttendanceDAO();

                // Users
                User admin = createUser(conn, "Alice Admin", "admin@example.com", "admin123", "admin");
                User trainer = createUser(conn, "Tom Trainer", "trainer@example.com", "train123", "trainer");
                User member = createUser(conn, "Molly Member", "member@example.com", "member123", "member");

                // Plans
                MembershipPlan gold = createPlan(conn, "Gold", "Unlimited 3-month access", 99.99, 90);
                MembershipPlan silver = createPlan(conn, "Silver", "1 month access", 39.99, 30);

                // Membership
                Membership membership = new Membership();
                membership.setUserId(member.getId());
                membership.setPlanId(gold.getId());
                membership.setStartDate(LocalDate.now());
                membership.setEndDate(LocalDate.now().plusDays(gold.getDurationDays()));
                membershipDAO.save(membership);

                // Class
                WorkoutClass yoga = new WorkoutClass();
                yoga.setName("Power Yoga");
                yoga.setDescription("High-intensity yoga flow");
                yoga.setSchedule("Mon/Wed 5:30 PM");
                yoga.setTrainerId(trainer.getId());
                workoutClassDAO.create(yoga);

                // Attendance
                attendanceDAO.logAttendance(member.getId(), yoga.getId());

                Log.success("✅ Database seeded successfully");

            } catch (SQLException | DatabaseException e) {
                throw new RuntimeException("Seeding failed", e);
            }
        });
    }

    private static void clearData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM class_attendance");
            stmt.executeUpdate("DELETE FROM memberships");
            stmt.executeUpdate("DELETE FROM workout_classes");
            stmt.executeUpdate("DELETE FROM membership_plans");
            stmt.executeUpdate("DELETE FROM users");
        }
    }

    private static User createUser(Connection conn, String name, String email, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));
            stmt.setString(4, role);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setRole(role);
                    return user;
                }
            }
        }
        throw new SQLException("Failed to create user: " + name);
    }

    private static MembershipPlan createPlan(Connection conn, String name, String description, double price, int durationDays) throws SQLException {
        String sql = "INSERT INTO membership_plans (name, description, price, duration_days) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, durationDays);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    MembershipPlan plan = new MembershipPlan();
                    plan.setId(rs.getInt(1));
                    plan.setName(name);
                    plan.setDescription(description);
                    plan.setPrice(price);
                    plan.setDurationDays(durationDays);
                    return plan;
                }
            }
        }
        throw new SQLException("Failed to create plan: " + name);
    }
}
