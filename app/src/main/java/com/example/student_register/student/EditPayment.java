package com.example.student_register.student;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.student_register.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditPayment {

    private Activity activity;

    private EditText payment;
    private FloatingActionButton delete, edit, sms, save, editCheck, deleteCheck, smsCheck;
    private ProgressBar delete_prog, edit_prog, sms_prog;

    public EditPayment() {

    }

    public void editSinglePayment(Activity activity, String studentId, String paymentId, int amount, int totalPrice, String date, int number) {

        Dialog dialog2 = new Dialog(activity);
        dialog2.setContentView(R.layout.payment_edit_payment);
        dialog2.setCancelable(true);
        dialog2.show();

        this.activity = activity;

        this.payment = dialog2.findViewById(R.id.edit_payment_amount);
        this.payment.setEnabled(false);
        this.payment.setText(amount + " DKK");

        this.delete = dialog2.findViewById(R.id.floatingActionButtonDelete);
        this.editCheck = dialog2.findViewById(R.id.floatingActionButtonEditCheck);
        this.deleteCheck = dialog2.findViewById(R.id.floatingActionButtonDeleteCheck);
        this.delete_prog = dialog2.findViewById(R.id.delete_progressBar);
        this.delete.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Delete Payment");
            builder.setMessage("Are you sure that you want to delete the following payment " + "\n" +
                    payment.getText().toString() + "\n" + date + "?")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        delete.setVisibility(View.INVISIBLE);
                        delete_prog.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DocumentReference paymentRef = FirebaseFirestore.getInstance()
                                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("student").document(studentId)
                                        .collection("payment").document(paymentId);
                                paymentRef.delete().addOnSuccessListener(aVoid -> {
                                    deleteCheck.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(() -> dialog2.cancel(),750);
                                }).addOnFailureListener(e -> {
                                    delete.setVisibility(View.VISIBLE);
                                    delete_prog.setVisibility(View.INVISIBLE);
                                    Toast.makeText(activity, "Error", 0).show();
                                });
                            }
                        },500);
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        dialog.cancel();
                    });
            AlertDialog alert = builder.create();
            alert.show();

        });

        this.edit = dialog2.findViewById(R.id.floatingActionButtonEdit);
        this.edit_prog = dialog2.findViewById(R.id.edit_progressBar);
        this.edit.setOnClickListener(v -> {
            edit.setVisibility(View.INVISIBLE);
            edit_prog.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                edit_prog.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
                payment.setEnabled(true);
                payment.setText(amount + "");
            },500);
        });

        this.save = dialog2.findViewById(R.id.floatingActionButtonSave);
        this.save.setOnClickListener(v -> {
            save.setVisibility(View.INVISIBLE);
            edit_prog.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (payment.getText().toString().trim().isEmpty()) {
                        Toast.makeText(activity, "A payment cannot empty", 0).show();
                        save.setVisibility(View.VISIBLE);
                        edit_prog.setVisibility(View.INVISIBLE);
                        return;
                    } else if (Integer.parseInt(payment.getText().toString()) == 0) {
                        Toast.makeText(activity, "A payment cannot be 0", 0).show();
                        save.setVisibility(View.VISIBLE);
                        edit_prog.setVisibility(View.INVISIBLE);
                        return;
                    } else if (Integer.parseInt(payment.getText().toString()) > totalPrice) {
                        Toast.makeText(activity, "A payment cannot be bigger than the total amount to be paid", 1).show();
                        save.setVisibility(View.VISIBLE);
                        edit_prog.setVisibility(View.INVISIBLE);
                        return;
                    }else{
                        int newPayment = Integer.parseInt(payment.getText().toString());
                        HashMap<String, Object> paymentMap = new HashMap<>();
                        paymentMap.put("payment", newPayment);
                        DocumentReference paymentRef = FirebaseFirestore.getInstance()
                                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection("student").document(studentId)
                                .collection("payment").document(paymentId);
                        paymentRef.update(paymentMap).addOnSuccessListener(aVoid -> {
                            edit_prog.setVisibility(View.INVISIBLE);
                            editCheck.setVisibility(View.VISIBLE);
                            payment.setEnabled(false);
                            payment.setText(newPayment + " DKK");
                            editCheck.setEnabled(false);
                        }).addOnFailureListener(e -> {
                            edit_prog.setVisibility(View.INVISIBLE);
                            edit.setVisibility(View.VISIBLE);
                            payment.setEnabled(false);
                            payment.setText(amount + " DKK");
                            Toast.makeText(activity, "Error", 0).show();
                        });
                    }
                }
            },500);
        });

        this.sms = dialog2.findViewById(R.id.floatingActionButtonSMS);
        this.sms_prog = dialog2.findViewById(R.id.sms_progressBar);
        this.smsCheck = dialog2.findViewById(R.id.floatingActionButtonSMSCheck);
        this.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms.setVisibility(View.INVISIBLE);
                sms_prog.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smsCheck.setVisibility(View.VISIBLE);
                        smsCheck.setEnabled(false);
                        sms_prog.setVisibility(View.INVISIBLE);
                        ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
                        String messageContent = payment.getText().toString().trim() + "\n" + date;
                        String numbertoString = number + "";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(numbertoString, null, messageContent, null, null);
                    }
                },1250);
            }
        });

    }

}
