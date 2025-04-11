package com.gym.workout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gym.db.DatabaseConnection;

public class ClassRegistrationDAO {
    public void register(int userId, int classId) throws SQLException {
        String sql = "INSERT INTO class_registrations (user_id, class_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, classId);
            stmt.executeUpdate();
        }
    }
}
