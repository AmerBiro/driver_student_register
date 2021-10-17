package com.example.student_register.data.mvvm.model;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class PaymentModel {

    @DocumentId
    private String paymentId;
    private Date date;
    private int payment;

    public PaymentModel(String paymentId, Date date, int payment) {
        this.paymentId = paymentId;
        this.date = date;
        this.payment = payment;
    }

    public PaymentModel() {

    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
