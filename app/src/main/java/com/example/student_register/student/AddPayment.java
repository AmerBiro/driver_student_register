package com.example.student_register.student;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.student_register.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class AddPayment {
    private Activity activity;

    private String studentId;
    private int payment;
    private Date currentTime;

    public AddPayment() {

    }

    public void addSinglePayment(Activity activity, String studentId, int totalPrice, int number, String name) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.payment_add_payment);
        dialog.setCancelable(true);
        dialog.show();
        EditText amount;
        Button add_payment_button;
        ProgressBar add_payment_progress_bar;

        amount = dialog.findViewById(R.id.add_payment_amount);
        add_payment_button = dialog.findViewById(R.id.add_payment_add_payment_button);
        add_payment_progress_bar = dialog.findViewById(R.id.add_payment_progressBar);

        this.activity = activity;
        this.studentId = studentId;
        this.currentTime = Calendar.getInstance().getTime();

        add_payment_button.setOnClickListener(v -> {
            if (amount.getText().toString().trim().isEmpty()) {
                Toast.makeText(activity, "A payment cannot empty", 0).show();
                return;
            } else if (Integer.parseInt(amount.getText().toString()) == 0) {
                Toast.makeText(activity, "A payment cannot be 0", 0).show();
                return;
            } else if (Integer.parseInt(amount.getText().toString()) > totalPrice) {
                Toast.makeText(activity, "A payment cannot be bigger than the total amount to be paid", 1).show();
                return;
            } else {
                add_payment_button.setVisibility(View.INVISIBLE);
                add_payment_progress_bar.setVisibility(View.VISIBLE);
                this.payment = Integer.parseInt(amount.getText().toString());

                HashMap<String, Object> payment = new HashMap<>();
                payment.put("date", this.currentTime);
                payment.put("payment", this.payment);

                CollectionReference paymentRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("student").document(this.studentId)
                        .collection("payment");

                paymentRef.add(payment).addOnSuccessListener(documentReference -> {
                    add_payment_progress_bar.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Send sms");
                    builder.setMessage("Do you want to send an sms to the the student with the added payment " + "\n" +
                            this.payment + " DKK" +  "\n" + currentTime + "?")
                            .setPositiveButton("Yes", (dialog2, id) -> {
                                ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
                                String messageContent = "Dear" + " " + name +  "\n\nWe have received \n" + this.payment + " DKK" + "\n" + currentTime + "\n\n" +  "Need to be paid" + "\n" +  (totalPrice - this.payment) + " DKK" ;
                                String numbertoString = number + "";
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(numbertoString, null, messageContent, null, null);
                            })
                            .setNegativeButton("No", (dialog2, id) -> {
                                dialog2.cancel();
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    dialog.cancel();
                }).addOnFailureListener(e -> {
                    add_payment_button.setVisibility(View.VISIBLE);
                    add_payment_progress_bar.setVisibility(View.INVISIBLE);
                    dialog.cancel();
                });
            }
        });


    }
}
