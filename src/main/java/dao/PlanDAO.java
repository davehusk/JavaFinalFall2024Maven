package dao;

import exceptions.DatabaseException;
import model.MembershipPlan;
import util.DBConnection;
import util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanDAO {

    private static final String GET_ALL_SQL = "SELECT * FROM membership_plans ORDER BY price";
    private static final String GET_BY_ID_SQL = "SELECT * FROM membership_plans WHERE id = ?";
    private static final String CREATE_SQL = "INSERT INTO membership_plans (name, description, price, duration_days) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE membership_plans SET name = ?, description = ?, price = ?, duration_days = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM membership_plans WHERE id = ?";
    private static final String JOIN_MEMBERSHIPS_SQL = 
        "SELECT u.name, p.name AS plan, m.start_date, m.end_date " +
        "FROM memberships m " +
        "JOIN users u ON u.id = m.user_id " +
        "JOIN membership_plans p ON p.id = m.plan_id";

    private static final String REVENUE_SQL =
        "SELECT SUM(p.price) FROM memberships m " +
        "JOIN membership_plans p ON m.plan_id = p.id";

    public List<MembershipPlan> getAllPlans() throws DatabaseException {
        List<MembershipPlan> plans = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {

            while (rs.next()) {
                plans.add(mapPlan(rs));
            }

        } catch (SQLException e) {
            Log.error("Database error fetching all plans", e);
            throw new DatabaseException("Failed to retrieve membership plans", e);
        }

        return plans;
    }

    public Optional<MembershipPlan> getPlanById(int id) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapPlan(rs));
            }

        } catch (SQLException e) {
            Log.error("Database error fetching plan by ID: " + id, e);
            throw new DatabaseException("Failed to retrieve plan", e);
        }

        return Optional.empty();
    }

    public boolean createPlan(MembershipPlan plan) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {

            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setDouble(3, plan.getPrice());
            stmt.setInt(4, plan.getDurationDays());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Log.error("Failed to create plan", e);
            throw new DatabaseException("Failed to create plan", e);
        }
    }

    public boolean updatePlan(MembershipPlan plan) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setDouble(3, plan.getPrice());
            stmt.setInt(4, plan.getDurationDays());
            stmt.setInt(5, plan.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Log.error("Failed to update plan", e);
            throw new DatabaseException("Failed to update plan", e);
        }
    }

    public boolean deletePlan(int planId) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, planId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Log.error("Failed to delete plan", e);
            throw new DatabaseException("Failed to delete plan", e);
        }
    }

    public void printMembershipJoin() {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(JOIN_MEMBERSHIPS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("📋 All Memberships:");
            while (rs.next()) {
                System.out.printf("%s - %s: %s to %s\n",
                    rs.getString("name"),
                    rs.getString("plan"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"));
            }

        } catch (SQLException e) {
            Log.error("Failed to view memberships: " + e.getMessage());
        }
    }

    public double calculateRevenue() {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(REVENUE_SQL);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            Log.error("Revenue calculation failed: " + e.getMessage());
        }
        return 0;
    }

    private MembershipPlan mapPlan(ResultSet rs) throws SQLException {
        MembershipPlan p = new MembershipPlan();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setDurationDays(rs.getInt("duration_days"));
        return p;
    }
}
