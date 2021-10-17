package com.example.student_register.ui.student.edit;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.student_register.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditPackagePrice {

    private Activity activity;

    private EditText edit_price;
    private FloatingActionButton edit_price_edit_button, edit_price_save_button, edit_price_check_button;
    private ProgressBar edit_price_progress_bar;

    public EditPackagePrice() {

    }

    public void editPackagePrice(Activity activity, String studentId, int packagePrice) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.payment_edit_package_price);
        dialog.setCancelable(true);
        dialog.show();

        this.activity = activity;

        this.edit_price = dialog.findViewById(R.id.edit_price);
        this.edit_price.setEnabled(false);
        this.edit_price.setText(packagePrice + " DKK");

        this.edit_price_edit_button = dialog.findViewById(R.id.edit_price_edit_button);
        this.edit_price_save_button = dialog.findViewById(R.id.edit_price_save_button);
        this.edit_price_check_button = dialog.findViewById(R.id.edit_price_check_button);
        this.edit_price_progress_bar = dialog.findViewById(R.id.edit_price_progress_bar);

        this.edit_price_edit_button.setOnClickListener(v -> {
            this.edit_price_edit_button.setVisibility(View.INVISIBLE);
            this.edit_price_progress_bar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    edit_price_progress_bar.setVisibility(View.INVISIBLE);
                    edit_price_save_button.setVisibility(View.VISIBLE);
                    edit_price.setEnabled(true);
                    edit_price.setText(packagePrice + "");

                    edit_price_save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edit_price_progress_bar.setVisibility(View.VISIBLE);
                            edit_price_save_button.setVisibility(View.INVISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    int newPrice = Integer.parseInt(edit_price.getText().toString());

                                    HashMap<String, Object> packageMap = new HashMap<>();
                                    packageMap.put("price", newPrice);

                                    DocumentReference priceRef = FirebaseFirestore.getInstance()
                                            .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .collection("student").document(studentId);
                                    priceRef.update(packageMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            edit_price_progress_bar.setVisibility(View.INVISIBLE);
                                            edit_price_check_button.setVisibility(View.VISIBLE);
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
                                            edit_price_progress_bar.setVisibility(View.INVISIBLE);
                                            edit_price_check_button.setVisibility(View.VISIBLE);
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
