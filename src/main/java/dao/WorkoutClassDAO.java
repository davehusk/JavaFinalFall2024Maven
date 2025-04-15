package dao;

import exceptions.DatabaseException;
import model.WorkoutClass;
import util.DBConnection;
import util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDAO {
    private static final String CREATE_SQL =
        "INSERT INTO workout_classes (name, description, schedule, trainer_id) VALUES (?, ?, ?, ?)";

    private static final String GET_ALL_SQL =
        "SELECT * FROM workout_classes ORDER BY name";

    private static final String GET_BY_TRAINER_SQL =
        "SELECT * FROM workout_classes WHERE trainer_id = ? ORDER BY schedule";

    private static final String DELETE_SQL =
        "DELETE FROM workout_classes WHERE id = ? AND trainer_id = ?";

    public boolean create(WorkoutClass wc) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, wc.getName());
            stmt.setString(2, wc.getDescription());
            stmt.setString(3, wc.getSchedule());
            stmt.setInt(4, wc.getTrainerId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Creating class failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    wc.setId(generatedKeys.getInt(1));
                }
            }
            return true;

        } catch (SQLException e) {
            Log.error("Error creating workout class: " + wc.getName(), e);
            throw new DatabaseException("Failed to create workout class", e);
        }
    }

    public List<WorkoutClass> getAll() throws DatabaseException {
        List<WorkoutClass> classes = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {

            while (rs.next()) {
                classes.add(mapWorkoutClass(rs));
            }

        } catch (SQLException e) {
            Log.error("Error fetching all workout classes", e);
            throw new DatabaseException("Failed to fetch workout classes", e);
        }

        return classes;
    }

    public List<WorkoutClass> getByTrainer(int trainerId) throws DatabaseException {
        List<WorkoutClass> classes = new ArrayList<>();

        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_TRAINER_SQL)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classes.add(mapWorkoutClass(rs));
            }

        } catch (SQLException e) {
            Log.error("Error fetching trainer's classes", e);
            throw new DatabaseException("Failed to fetch trainer classes", e);
        }

        return classes;
    }

    public boolean delete(int classId, int trainerId) throws DatabaseException {
        try (Connection conn = DBConnection.get();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, classId);
            stmt.setInt(2, trainerId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Log.error("Error deleting class ID " + classId, e);
            throw new DatabaseException("Failed to delete class", e);
        }
    }

    private WorkoutClass mapWorkoutClass(ResultSet rs) throws SQLException {
        WorkoutClass wc = new WorkoutClass();
        wc.setId(rs.getInt("id"));
        wc.setName(rs.getString("name"));
        wc.setDescription(rs.getString("description"));
        wc.setSchedule(rs.getString("schedule"));
        wc.setTrainerId(rs.getInt("trainer_id"));
        return wc;
    }
}
