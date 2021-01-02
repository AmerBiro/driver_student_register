package com.example.driverstudentregister.mvvm;

public class StudentModel {

    private String name, street, zip_code, date;
    private int phone, cpr;

    public StudentModel() {
    }

    public StudentModel(String name, String street, String zip_code, String date, int phone, int cpr) {
        this.name = name;
        this.street = street;
        this.zip_code = zip_code;
        this.date = date;
        this.phone = phone;
        this.cpr = cpr;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getDate() {
        return date;
    }

    public int getPhone() {
        return phone;
    }

    public int getCpr() {
        return cpr;
    }
}
