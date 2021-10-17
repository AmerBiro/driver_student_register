package com.example.student_register.ui.student.edit;

import android.app.Activity;
import android.icu.util.Calendar;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class CreateStudent {

    private String name, street, city;
    private String note, email;
    private int phone, zip_code, cpr;
    private int price, discount;
    private String studentId;
    private int i;

    private Activity activity;
    private NavController controller;
    private View view;

    public CreateStudent(Activity activity, NavController controller, View view) {
        this.activity = activity;
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(this.view);
    }

    public void create(EditText name, EditText phone, EditText street, EditText zip_code, EditText city, EditText cpr, EditText price, EditText discount, EditText note, EditText email,
                       Button button, ProgressBar progressBar,
                       int action) {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, Object> student = new HashMap<>();

        this.name = name.getText().toString();
        this.street = street.getText().toString();
        this.city = city.getText().toString();
        this.note = note.getText().toString();
        this.email = email.getText().toString();

        student.put("name", this.name);
        student.put("street", this.street);
        student.put("city", this.city);

        Date currentTime = Calendar.getInstance().getTime();

        student.put("date", currentTime);
        student.put("note", this.note);
        student.put("email", this.email);

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
                studentId = documentReference.getId();

                CollectionReference theoryRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("student").document(studentId)
                        .collection("theory");

                HashMap<String, Object> theory = new HashMap<>();
                theory.put("title", "Lecture");

                for (int i = 0; i<8; i++){
                    theory.put("number", i);
                    theoryRef.add(theory);
                }



                CollectionReference practiseRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("student").document(studentId)
                        .collection("practise");

                HashMap<String, Object> practise = new HashMap<>();
                practise.put("title", "Practise");

                for (int i = 0; i<10; i++){
                    practise.put("number", i);
                    practiseRef.add(practise);
                }

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
