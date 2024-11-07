package com.keyin.cars;

import com.keyin.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO {

    public void getProducts() throws SQLException {


        ResultSet rs = null;
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int product_id = rs.getInt("product_id");
                String product_name = rs.getString("product_name");
                double product_price = rs.getDouble("product_price");
                int product_quantity = rs.getInt("product_quantity");
                int seller_id = rs.getInt("seller_id");

                System.out.println("product id: " + product_id);
                System.out.println("product name: " +product_name);
                System.out.println("product price: " +product_price);
                System.out.println("product quantity: " +product_quantity);
                System.out.println("product seller id : " +seller_id);
                System.out.println("------------------------------------");
            }

        };
}
}
