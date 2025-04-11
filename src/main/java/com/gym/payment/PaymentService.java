package com.gym.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.gym.db.DatabaseConnection;
import com.gym.membership.Membership;
import com.gym.membership.MembershipDAO;
import com.gym.user.User;

public class PaymentService {
    private final MembershipDAO membershipDAO = new MembershipDAO();

    public void renewMembership(User user) throws SQLException {
        LocalDate now = LocalDate.now();
        LocalDate newEnd = now.plusMonths(1);

        Membership membership = new Membership(user.getId(), "Standard", now, newEnd);
        membershipDAO.create(membership);

        recordPayment(user.getId(), 50.00); // flat rate
        System.out.println("Membership renewed until " + newEnd);
    }

    private void recordPayment(int userId, double amount) throws SQLException {
        String sql = "INSERT INTO payments (user_id, amount, paid_at) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        }
    }
}
