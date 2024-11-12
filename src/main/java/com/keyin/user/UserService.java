package com.keyin.user;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

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

    public User login(String username, String password) throws SQLException{
        if(username == null || password == null){
            System.out.println("The User Does Not Exist");
        }

        User user = userDAO.getUserByUsername(username);

        if(user == null){
            System.out.println("The User Does Not Exist! ");
            return null;
        }

        if(!BCrypt.checkpw(password, user.getPassword())){
            System.out.println("Wrong Password, Please Try Again!");
            return null;
        }

        System.out.println("User Has Passed Auth");

        return user;
    }





}
