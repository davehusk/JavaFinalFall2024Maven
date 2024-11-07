package com.keyin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecomapplication2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "jordan1234";

    public static Connection getConnection() throws SQLException, SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
