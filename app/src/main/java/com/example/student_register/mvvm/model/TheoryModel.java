package com.example.student_register.mvvm.model;

import com.google.firebase.firestore.DocumentId;
import java.util.Date;

public class TheoryModel {

    @DocumentId
    private String theoryId;
    private String title;
    private String date;
    private int number;

    public TheoryModel() {

    }

    public TheoryModel(String theoryId, String title, String date, int number) {
        this.theoryId = theoryId;
        this.title = title;
        this.date = date;
        this.number = number;
    }

    public String getTheoryId() {
        return theoryId;
    }

    public void setTheoryId(String theoryId) {
        this.theoryId = theoryId;
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
