package com.keyin.cars;

import java.sql.SQLException;

public class CarService {

    CarDAO carDAO = new CarDAO();

    public void listAllCars() throws SQLException {
        carDAO.getAllCars();
    }

    public boolean createNewCar(Car car) throws SQLException {

        if(car == null){
            return false;
        }

        carDAO.addNewCar(car);
        System.out.println("Car Was Created! Woo!");
        return true;
    }

}
