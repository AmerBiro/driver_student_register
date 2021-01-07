package com.example.student_register.mvvm;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateStudent {

    private String name, phone, street, zip_code, city, cpr, date;
    private String price, discount;
    private String lecture1, lecture2, lecture3, lecture4, lecture5, lecture6, lecture7, lecture8;
    private String practise1, practise2, practise3, practise4, practise5, practise6, practise7, practise8, practise9, practise10;
    private String note;

    public CreateStudent() {

    }

    public void setInfo(String name, String phone, String street, String zip_code, String city, String cpr, String date) {
        this.name = name;
        this.phone = phone;
        this.street = street;
        this.zip_code = zip_code;
        this.city = city;
        this.cpr = cpr;
        this.date = date;
    }

    public void setPrice(String price, String discount) {
        this.price = price;
        this.discount = discount;
    }

    public void setLecture(String lecture1, String lecture2, String lecture3, String lecture4, String lecture5, String lecture6, String lecture7, String lecture8) {
        this.lecture1 = lecture1;
        this.lecture2 = lecture2;
        this.lecture3 = lecture3;
        this.lecture4 = lecture4;
        this.lecture5 = lecture5;
        this.lecture6 = lecture6;
        this.lecture7 = lecture7;
        this.lecture8 = lecture8;
    }

    public void setPractise(String practise1, String practise2, String practise3, String practise4, String practise5, String practise6, String practise7, String practise8, String practise9, String practise10) {
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

    public void setNote(String note) {
        this.note = note;
    }

    public void createStudent() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentStudent = firebaseAuth.getCurrentUser();
        String userId = currentStudent.getUid();
        CollectionReference reference = FirebaseFirestore.getInstance()
                .collection("user").document(userId)
                .collection("student");


        Map<String, Object> student = new HashMap<>();

        student.put("name", this.name);
        student.put("phone", this.phone);
        student.put("street", this.street);
        student.put("zip_code", this.zip_code);
        student.put("city", this.city);
        student.put("cpr", this.cpr);
        student.put("date", this.date);
        student.put("price", price);
        student.put("discount", discount);
        student.put("lecture1", "");
        student.put("lecture2", "");
        student.put("lecture3", "");
        student.put("lecture4", "");
        student.put("lecture5", "");
        student.put("lecture6", "");
        student.put("lecture7", "");
        student.put("lecture8", "");
        student.put("practise1", "");
        student.put("practise2", "");
        student.put("practise3", "");
        student.put("practise4", "");
        student.put("practise5", "");
        student.put("practise6", "");
        student.put("practise7", "");
        student.put("practise8", "");
        student.put("practise9", "");
        student.put("practise10", "");
        student.put("note", this.note);

        reference.add(student);
    }


}
