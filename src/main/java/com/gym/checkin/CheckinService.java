package com.gym.checkin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.gym.db.DatabaseConnection;
import com.gym.user.User;

public class CheckinService {

    public void recordCheckin(User user) throws SQLException {
        String sql = "INSERT INTO checkins (user_id, date) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
            System.out.println("Checked in successfully!");
        }
    }

    public void viewCheckinHistory(User user) throws SQLException {
        String sql = "SELECT date FROM checkins WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            System.out.println("Your Check-in History:");
            while (rs.next()) {
                System.out.println("- " + rs.getDate("date"));
            }
        }
    }

    public void viewCheckinsToday() throws SQLException {
        String sql = """
            SELECT u.username, u.role
            FROM checkins c
            JOIN users u ON c.user_id = u.id
            WHERE c.date = CURRENT_DATE
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("Today's Check-ins:");
            while (rs.next()) {
                System.out.printf("- %s (%s)%n", rs.getString("username"), rs.getString("role"));
            }
        }
    }
}
