package com.keyin.cars;

import com.keyin.database.DatabaseConnection;
import com.keyin.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO {

    public void getAllCars() throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM cars";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int car_id = rs.getInt("car_id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                double price = rs.getDouble("price");
                int seller_id = rs.getInt("seller_id");

                System.out.println("-----------------");
                System.out.println("Car ID: " + car_id);
                System.out.println("Make : " + make);
                System.out.println("Model : " + model);
                System.out.println("Price : " + price);
                System.out.println("Seller ID: " + seller_id);
                System.out.println("-------------------------");
            }

        };
    }

    public void addNewCar(Car car) throws SQLException{
        String sql = "INSERT INTO cars (make,model,year, price,seller_id) VALUES (?, ?, ?, ?,?)";

        try(Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, car.getMake());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getPrice());
            preparedStatement.setInt(5, car.getSeller_id());

            preparedStatement.executeUpdate();

        }
    }

    public ResultSet getAllCarsForUser(User user) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM cars where seller_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,user.getUser_id());
            rs = pstmt.executeQuery();
            System.out.println("List Of Cars For Owner " + user.getUsername()+ " " + user.getUser_id());
            while (rs.next()) {
                int car_id = rs.getInt("car_id");
                String make = rs.getString("make");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double price = rs.getInt("price");
                int seller_id = rs.getInt("seller_id");

                System.out.println("Car Id: " + car_id );
                System.out.println("make: " + make);
                System.out.println("Model " + model);
                System.out.println("year: " + year);
                System.out.println("price : " + price);
                System.out.println("------------------------------------");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return rs;
    }




}
