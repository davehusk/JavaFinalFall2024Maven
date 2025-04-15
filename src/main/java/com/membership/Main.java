package com.membership;

import core.MainMenu;
import util.DBConnection;
import util.Log;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database connection pool
            DBConnection.get();
            
            // Start application
            new MainMenu().show();
            
        } catch (Exception e) {
            Log.error("Application failed to start: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.close();
            Log.info("Application shutdown complete");
        }
    }
}