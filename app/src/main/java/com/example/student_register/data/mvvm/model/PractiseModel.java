package com.example.student_register.data.mvvm.model;

import com.google.firebase.firestore.DocumentId;

public class PractiseModel {

    @DocumentId
    private String practiseId;
    private String title;
    private String date;
    private int number;

    public PractiseModel() {

    }

    public PractiseModel(String practiseId, String title, String date, int number) {
        this.practiseId = practiseId;
        this.title = title;
        this.date = date;
        this.number = number;
    }

    public String getPractiseId() {
        return practiseId;
    }

    public void setPractiseId(String practiseId) {
        this.practiseId = practiseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
