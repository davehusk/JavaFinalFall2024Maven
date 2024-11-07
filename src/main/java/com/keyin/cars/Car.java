package com.keyin.cars;

public class Car {

    private int car_id;
    private String car_name;
    private String car_color;
    private String car_engine;
    private int car_year;
    private int seller_id;

    public Car(String car_name, String car_color, String car_engine, int car_year, int seller_id) {
        this.car_name = car_name;
        this.car_color = car_color;
        this.car_engine = car_engine;
        this.car_year = car_year;
        this.seller_id = seller_id;
    }

    public Car(int car_id, String car_name, String car_color, String car_engine, int car_year, int seller_id) {
        this.car_id = car_id;
        this.car_name = car_name;
        this.car_color = car_color;
        this.car_engine = car_engine;
        this.car_year = car_year;
        this.seller_id = seller_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_engine() {
        return car_engine;
    }

    public void setCar_engine(String car_engine) {
        this.car_engine = car_engine;
    }

    public int getCar_year() {
        return car_year;
    }

    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }
}
