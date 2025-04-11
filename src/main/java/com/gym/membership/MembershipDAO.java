package com.gym.membership;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gym.db.DatabaseConnection;

public class MembershipDAO {
    public Membership findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM memberships WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membership(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("membership_type"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate()
                );
            }
        }
        return null;
    }

    public void create(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (user_id, membership_type, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, membership.getUserId());
            stmt.setString(2, membership.getMembershipType());
            stmt.setDate(3, Date.valueOf(membership.getStartDate()));
            stmt.setDate(4, Date.valueOf(membership.getEndDate()));
            stmt.executeUpdate();
        }
    }
}
