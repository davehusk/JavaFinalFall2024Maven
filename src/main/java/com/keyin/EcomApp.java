package com.keyin;

import com.keyin.cars.Car;
import com.keyin.cars.CarService;
import com.keyin.user.User;
import com.keyin.user.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class EcomApp {
   private static final UserService userService = new UserService();
   private static final CarService carService = new CarService();
    public static void main(String[] args) throws SQLException {


//        userService.login("KHollet","kyle");

//        User user = userService.login("KHollet","kyle");
//
//        System.out.println(user.getEmail());
//        userService.getAllUsers();


//        User user = new User("KHollet","k@k.com","kyle");
//
//        userService.addUser(user);

        Scanner sc = new Scanner(System.in);

        int choice = 0;

            while(true){
                displayMainMenu(sc);

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    registerUser(sc);
                    break;
                case 2:
                    loginUser(sc);
                    break;

                    case 3:
                        System.out.println("Exiting App... Peace Out");
                        return;

                default:
                    break;
            }
        }


    }

    private static void displayMainMenu(Scanner sc) {
        System.out.println("Welcome To Our Aplication!");
        System.out.println("What Would You Like To Do?");
        System.out.println("1: Create A New User");
        System.out.println("2: Login The User");
        System.out.println("3: Exit Application");

    }

    private static void loginUser(Scanner sc) throws SQLException {
        System.out.println("Login Menu.....");
        System.out.println("Enter Username");
        String username = sc.next();
        System.out.println("Enter Password");
        String password = sc.next();

        User loggedInUser = userService.login(username,password);

        if(loggedInUser != null) {
            System.out.println("Login Successful");
            if(loggedInUser.getUsername().equals("KHollet")){
                kyleDashBoard(sc,loggedInUser);
            }
        } else{
            System.out.println("Login Failed");
        }
    }

    private static void kyleDashBoard(Scanner sc, User loggedInUser) throws SQLException {
        while(true){
            System.out.println("Welcome To Kyle Dash Board");
            System.out.println("Please Choose What You Wanna Do!");
            System.out.println("1: Print Kyle info");
            System.out.println("2: Create A New Car ");
            System.out.println("3: See All Cars For Kyle ");
            System.out.println("4: Exit Back To Main");
            int choice = sc.nextInt();

            if (choice == 1) {
                printKyleInfo(loggedInUser);
            } else if (choice == 2) {
                createNewCarDashBoard(sc,loggedInUser);
            } else if (choice == 3) {
                printCarsForUser(loggedInUser);
            } else{
                return;
            }


        }


    }

    private static void printCarsForUser(User loggedInUser) throws SQLException {

        carService.getCarForSellerById(loggedInUser);

    }

    private static void createNewCarDashBoard(Scanner sc, User loggedInUser) throws SQLException {
        System.out.println("Welcome To Create A New Car!");
        System.out.println("Please Choose What You Wanna Do!");
        System.out.println("Enter Car Make: ");
        String make = sc.next();
        System.out.println("Enter Car Model: ");
        String model = sc.next();
        System.out.println("Enter Car Year: ");
        int year = sc.nextInt();
        System.out.println("Enter Car Price: ");
        double price = sc.nextDouble();

        Car car = new Car(make,model,year,price,loggedInUser.getUser_id());

        carService.createNewCar(car);






    }


    private static void printKyleInfo(User loggedInUser) {
        System.out.println("Kyle Details:");
        System.out.println("Username: " + loggedInUser.getUsername());
        System.out.println("Password: " + loggedInUser.getPassword());
    }


    private static void registerUser(Scanner sc) throws SQLException {
        System.out.println("Create User Menu.......");
        System.out.println("------------------------------");
        System.out.println("Enter Username:");
        String userName = sc.next();
        System.out.println("Enter Email:");
        String email = sc.next();
        System.out.println("Enter Password:");
        String password = sc.next();
        System.out.println("---------------------------");

        User user = new User(userName,email,password);

        userService.addUser(user);
    }


}

