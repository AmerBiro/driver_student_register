package com.example.driverstudentregister.mvvm;

public class StudentModel {

    private String name, street, zip_code, city, date, phone, cpr;


    public StudentModel() {

    }

    public StudentModel(String name, String street, String zip_code, String city, String date, String phone, String cpr) {
        this.name = name;
        this.street = street;
        this.zip_code = zip_code;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getCpr() {
        return cpr;
    }
}
