package com.example.student_register.student;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateStudent {

    private String name, street, city, date;
    private String note;
    private int phone, zip_code, cpr;
    private int price, discount;

    private Activity activity;
    private NavController controller;
    private View view;

    public CreateStudent(Activity activity, NavController controller, View view) {
        this.activity = activity;
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(this.view);
    }

    public void create(EditText name, EditText phone, EditText street, EditText zip_code, EditText city, EditText cpr, EditText price, EditText discount, EditText note, String date,
                       Button button, ProgressBar progressBar,
                       int action) {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, Object> student = new HashMap<>();

        this.name = name.getText().toString();
        this.street = street.getText().toString();
        this.city = city.getText().toString();
        this.date = date;
        this.note = note.getText().toString();

        student.put("name", this.name);
        student.put("street", this.street);
        student.put("city", this.city);
        student.put("date", this.date);
        student.put("note", this.note);

        if (!phone.getText().toString().trim().isEmpty()) {
            this.phone = Integer.parseInt(phone.getText().toString());
            student.put("phone", this.phone);
        }

        if (!zip_code.getText().toString().trim().isEmpty()) {
            this.zip_code = Integer.parseInt(zip_code.getText().toString());
            student.put("zip_code", this.zip_code);
        }

        if (!cpr.getText().toString().trim().isEmpty()) {
            this.cpr = Integer.parseInt(cpr.getText().toString());
            student.put("cpr", this.cpr);
        }

        if (price.getText().toString().trim().isEmpty()) {
            this.price = 13500;
        } else {
            this.price = Integer.parseInt(price.getText().toString());
        }
        student.put("price", this.price);

        if (!discount.getText().toString().trim().isEmpty()) {
            this.discount = Integer.parseInt(discount.getText().toString());
            student.put("discount", this.discount);
        }


        CollectionReference studentRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("student");

        studentRef.add(student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "Student created successfully", 0).show();
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error creating student\n" + e.getMessage(), 1).show();
                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}