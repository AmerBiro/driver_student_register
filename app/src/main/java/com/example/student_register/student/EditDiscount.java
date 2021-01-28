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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.student_register.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditDiscount {

    private Activity activity;

    private EditText edit_discount;
    private FloatingActionButton edit_discount_edit_button, edit_discount_save_button, edit_discount_check_button;
    private ProgressBar edit_discount_progress_bar;

    public EditDiscount() {

    }

    public void editDiscountPrice(Activity activity, String studentId, int discount) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.payment_edit_discount);
        dialog.setCancelable(true);
        dialog.show();

        this.activity = activity;

        this.edit_discount = dialog.findViewById(R.id.edit_discount);
        this.edit_discount.setEnabled(false);
        this.edit_discount.setText(discount + " DKK");

        this.edit_discount_edit_button = dialog.findViewById(R.id.edit_discount_edit_button);
        this.edit_discount_save_button = dialog.findViewById(R.id.edit_discount_save_button);
        this.edit_discount_check_button = dialog.findViewById(R.id.edit_discount_check_button);
        this.edit_discount_progress_bar = dialog.findViewById(R.id.edit_discount_progress_bar);

        this.edit_discount_edit_button.setOnClickListener(v -> {
            this.edit_discount_edit_button.setVisibility(View.INVISIBLE);
            this.edit_discount_progress_bar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    edit_discount_progress_bar.setVisibility(View.INVISIBLE);
                    edit_discount_save_button.setVisibility(View.VISIBLE);
                    edit_discount.setEnabled(true);
                    edit_discount.setText(discount + "");

                    edit_discount_save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edit_discount_progress_bar.setVisibility(View.VISIBLE);
                            edit_discount_save_button.setVisibility(View.INVISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    int newPrice = Integer.parseInt(edit_discount.getText().toString());

                                    HashMap<String, Object> packageMap = new HashMap<>();
                                    packageMap.put("discount", newPrice);

                                    DocumentReference priceRef = FirebaseFirestore.getInstance()
                                            .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .collection("student").document(studentId);
                                    priceRef.update(packageMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            edit_discount_progress_bar.setVisibility(View.INVISIBLE);
                                            edit_discount_check_button.setVisibility(View.VISIBLE);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.cancel();
                                                }
                                            }, 500);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            edit_discount_progress_bar.setVisibility(View.INVISIBLE);
                                            edit_discount_check_button.setVisibility(View.VISIBLE);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.cancel();
                                                }
                                            }, 500);
                                        }
                                    });
                                }
                            }, 750);


                        }
                    });

                }
            }, 750);


        });

    }

}
