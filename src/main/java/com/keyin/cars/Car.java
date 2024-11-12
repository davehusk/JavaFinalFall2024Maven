package com.keyin.cars;

public class Car {

    private int car_id;
    private String make;
    private String model;
    private int year;
    private double price;
    private int seller_id;

    public Car(int car_id, String make, String model, int year, double price, int seller_id) {
        this.car_id = car_id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.seller_id = seller_id;
    }

    public Car(String make, String model, int year, double price, int seller_id) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.seller_id = seller_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }
}