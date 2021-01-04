package com.example.student_register.mvvm;

public class StudentModel {

    private String name, phone, street, zip_code, city, cpr, date;
    private String price, discount;
    private String lecture1,lecture2,lecture3,lecture4,lecture5,lecture6,lecture7, lecture8;
    private String practise1,practise2,practise3,practise4,practise5,practise6,practise7,practise8,practise9,practise10;
    private String note;

    public StudentModel() {
    }

    public StudentModel(String name, String phone, String street, String zip_code, String city, String cpr, String date) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.zip_code = zip_code;
        this.city = city;
        this.cpr = cpr;
        this.date = date;
    }

    public StudentModel(String price, String discount) {
        this.price = price;
        this.discount = discount;
    }

    public StudentModel(String lecture1, String lecture2, String lecture3, String lecture4, String lecture5, String lecture6, String lecture7, String lecture8) {
        this.lecture1 = lecture1;
        this.lecture2 = lecture2;
        this.lecture3 = lecture3;
        this.lecture4 = lecture4;
        this.lecture5 = lecture5;
        this.lecture6 = lecture6;
        this.lecture7 = lecture7;
        this.lecture8 = lecture8;
    }

    public StudentModel(String practise1, String practise2, String practise3, String practise4, String practise5, String practise6, String practise7, String practise8, String practise9, String practise10) {
        this.practise1 = practise1;
        this.practise2 = practise2;
        this.practise3 = practise3;
        this.practise4 = practise4;
        this.practise5 = practise5;
        this.practise6 = practise6;
        this.practise7 = practise7;
        this.practise8 = practise8;
        this.practise9 = practise9;
        this.practise10 = practise10;
    }

    public StudentModel(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
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

    public String getCpr() {
        return cpr;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getLecture1() {
        return lecture1;
    }

    public String getLecture2() {
        return lecture2;
    }

    public String getLecture3() {
        return lecture3;
    }

    public String getLecture4() {
        return lecture4;
    }

    public String getLecture5() {
        return lecture5;
    }

    public String getLecture6() {
        return lecture6;
    }

    public String getLecture7() {
        return lecture7;
    }

    public String getLecture8() {
        return lecture8;
    }

    public String getPractise1() {
        return practise1;
    }

    public String getPractise2() {
        return practise2;
    }

    public String getPractise3() {
        return practise3;
    }

    public String getPractise4() {
        return practise4;
    }

    public String getPractise5() {
        return practise5;
    }

    public String getPractise6() {
        return practise6;
    }

    public String getPractise7() {
        return practise7;
    }

    public String getPractise8() {
        return practise8;
    }

    public String getPractise9() {
        return practise9;
    }

    public String getPractise10() {
        return practise10;
    }

    public String getNote() {
        return note;
    }
}
