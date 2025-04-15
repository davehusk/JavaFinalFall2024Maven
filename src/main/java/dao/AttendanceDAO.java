package dao;

import exceptions.DatabaseException;
import util.DBConnection;
import util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private static final String LOG_ATTENDANCE_SQL =
        "INSERT INTO class_attendance (user_id, class_id, attended_at) VALUES (?, ?, CURRENT_DATE)";

    private static final String GET_HISTORY_SQL =
        "SELECT wc.name, ca.attended_at FROM class_attendance ca " +
        "JOIN workout_classes wc ON wc.id = ca.class_id WHERE ca.user_id = ?";

    private static final String GET_CLASS_ATTENDANCE_SQL =
        "SELECT u.name FROM class_attendance ca " +
        "JOIN users u ON ca.user_id = u.id WHERE ca.class_id = ?";

    public boolean logAttendance(int userId, int classId) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(LOG_ATTENDANCE_SQL)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, classId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Log.error("Error logging attendance for user " + userId + " to class " + classId, e);
            throw new DatabaseException("Failed to log attendance", e);
        }
    }

    public List<String> getHistory(int userId) throws DatabaseException {
        List<String> history = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(GET_HISTORY_SQL)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                history.add(String.format(
                    "%s - Attended on %s",
                    rs.getString("name"),
                    rs.getDate("attended_at")
                ));
            }

        } catch (SQLException e) {
            Log.error("Error fetching attendance history for user " + userId, e);
            throw new DatabaseException("Failed to fetch attendance history", e);
        }

        return history;
    }

    public List<String> getClassAttendance(int classId) throws DatabaseException {
        List<String> attendees = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(GET_CLASS_ATTENDANCE_SQL)) {

            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendees.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            Log.error("Error fetching attendance for class " + classId, e);
            throw new DatabaseException("Failed to fetch class attendance", e);
        }

        return attendees;
    }
}
