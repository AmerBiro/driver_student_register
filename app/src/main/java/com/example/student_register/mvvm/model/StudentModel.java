package com.example.student_register.mvvm.model;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class StudentModel {

    @DocumentId
    private String studentId;
    private String name, street, city;
    private Date date;
    private String note;
    private int phone, zip_code, cpr;
    private int price, discount;

    public StudentModel(String studentId, String name, String street, String city, Date date, String note, int phone, int zip_code, int cpr, int price, int discount) {
        this.studentId = studentId;
        this.name = name;
        this.street = street;
        this.city = city;
        this.date = date;
        this.note = note;
        this.phone = phone;
        this.zip_code = zip_code;
        this.cpr = cpr;
        this.price = price;
        this.discount = discount;
    }

    public StudentModel() {

    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public int getCpr() {
        return cpr;
    }

    public void setCpr(int cpr) {
        this.cpr = cpr;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
