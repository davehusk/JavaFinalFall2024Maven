package util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    public static void execute(TransactionalOperation operation) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            conn.setAutoCommit(false);
            
            operation.execute(conn);
            conn.commit();
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Log.error("Failed to rollback transaction: " + ex.getMessage());
                }
            }
            Log.error("Transaction failed: " + e.getMessage());
            throw new RuntimeException("Transaction failed", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    Log.error("Failed to close connection: " + e.getMessage());
                }
            }
        }
    }

    @FunctionalInterface
    public interface TransactionalOperation {
        void execute(Connection conn) throws SQLException;
    }
}