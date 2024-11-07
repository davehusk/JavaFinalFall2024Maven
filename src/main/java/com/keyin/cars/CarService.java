package com.keyin.cars;

import java.sql.SQLException;

public class CarService {

    private CarDAO carDAO;

    public CarService() {
        carDAO = new CarDAO();

    }

    public void listAllProducts() throws SQLException {
        carDAO.getProducts();
    }
}
