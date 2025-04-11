package com.gym.workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gym.db.DatabaseConnection;

public class WorkoutClassDAO {
    public void create(WorkoutClass workoutClass) throws SQLException {
        String sql = "INSERT INTO workout_classes (class_name, trainer_id, schedule) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, workoutClass.getClassName());
            stmt.setInt(2, workoutClass.getTrainerId());
            stmt.setTimestamp(3, Timestamp.valueOf(workoutClass.getSchedule()));
            stmt.executeUpdate();
        }
    }

    public void update(int id, String name, LocalDateTime schedule) throws SQLException {
        String sql = "UPDATE workout_classes SET class_name = ?, schedule = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setTimestamp(2, Timestamp.valueOf(schedule));
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM workout_classes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<WorkoutClass> findByTrainerId(int trainerId) throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes WHERE trainer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classes.add(new WorkoutClass(
                    rs.getInt("id"),
                    rs.getString("class_name"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("schedule").toLocalDateTime()
                ));
            }
        }
        return classes;
    }

    public List<WorkoutClass> findAll() throws SQLException {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes ORDER BY schedule";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                classes.add(new WorkoutClass(
                    rs.getInt("id"),
                    rs.getString("class_name"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("schedule").toLocalDateTime()
                ));
            }
        }
        return classes;
    }

    public void printClassRoster(int classId) throws SQLException {
        String sql = """
            SELECT u.username, u.email
            FROM class_registrations cr
            JOIN users u ON cr.user_id = u.id
            WHERE cr.class_id = ?
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Enrolled Members:");
            while (rs.next()) {
                System.out.printf("- %s (%s)%n", rs.getString("username"), rs.getString("email"));
            }
        }
    }
}
