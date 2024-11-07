package com.keyin;

import com.keyin.cars.CarService;

import java.sql.SQLException;

public class EcomApp {
   private static final CarService carService = new CarService();
    public static void main(String[] args) throws SQLException {


        carService.listAllProducts();
    }
}
