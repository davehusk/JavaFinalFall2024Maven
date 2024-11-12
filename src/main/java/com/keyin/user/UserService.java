package com.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public void getAllUsers() throws SQLException {

        userDAO.getAllUsers();

    }

    public boolean addUser(User user) throws SQLException{
        if(user.equals(null)){
            System.out.println("User Is Null");
            return false;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        User newUser = new User(user.getUsername(),user.getEmail(), hashedPassword);
        userDAO.addUser(newUser);
        System.out.println("User Created");


        return true;
    }





}
