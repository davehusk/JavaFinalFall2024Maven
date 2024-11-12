package com.keyin;

import com.keyin.cars.CarService;
import com.keyin.user.User;
import com.keyin.user.UserService;

import java.sql.SQLException;

public class EcomApp {
   private static final UserService userService = new UserService();
    public static void main(String[] args) throws SQLException {


//        userService.login("KHollet","kyle");

        User user = userService.login("KHollet","kyle");

        System.out.println(user.getEmail());
        userService.getAllUsers();


//        User user = new User("KHollet","k@k.com","kyle");
//
//        userService.addUser(user);





    }
}
