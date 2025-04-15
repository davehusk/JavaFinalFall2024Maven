package dao;

import exceptions.DatabaseException;
import model.Membership;
import util.DBConnection;
import util.Log;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class MembershipDAO {
    private static final String GET_ACTIVE_SQL = "SELECT * FROM memberships WHERE user_id = ? AND end_date >= CURRENT_DATE";
    private static final String SAVE_SQL = "INSERT INTO memberships (user_id, plan_id, start_date, end_date) VALUES (?, ?, ?, ?)";

    public Optional<Membership> getActiveMembership(int userId) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(GET_ACTIVE_SQL)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Membership m = new Membership();
                m.setId(rs.getInt("id"));
                m.setUserId(rs.getInt("user_id"));
                m.setPlanId(rs.getInt("plan_id"));
                m.setStartDate(rs.getDate("start_date").toLocalDate());
                m.setEndDate(rs.getDate("end_date").toLocalDate());
                return Optional.of(m);
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            Log.error("Database error fetching active membership for user: " + userId, e);
            throw new DatabaseException("Failed to retrieve membership", e);
        }
    }

    public boolean save(Membership membership) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, membership.getUserId());
            stmt.setInt(2, membership.getPlanId());
            stmt.setDate(3, Date.valueOf(membership.getStartDate()));
            stmt.setDate(4, Date.valueOf(membership.getEndDate()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    membership.setId(generatedKeys.getInt(1));
                }
            }
            return true;
            
        } catch (SQLException e) {
            Log.error("Database error saving membership", e);
            throw new DatabaseException("Failed to save membership", e);
        }
    }
}
