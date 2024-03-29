package com.example.student_register.ui.student.edit;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditStudent {

    private String name, street, city, email;
    private int phone, zip_code, cpr;

    private Activity activity;
    private NavController controller;
    private View view;

    public EditStudent(Activity activity, NavController controller, View view) {
        this.activity = activity;
        this.controller = controller;
        this.view = view;
        this.controller = Navigation.findNavController(this.view);
    }

    public void editStudent(String studentId, EditText name, EditText phone, EditText street, EditText zip_code, EditText city, EditText cpr, EditText email,
                       Button button, ProgressBar progressBar,
                       int action) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            HashMap<String, Object> student = new HashMap<>();

            this.name = name.getText().toString();
            this.street = street.getText().toString();
            this.city = city.getText().toString();
            this.email = email.getText().toString();

            student.put("name", this.name);
            student.put("street", this.street);
            student.put("city", this.city);
            student.put("email", this.email);

            if (!phone.getText().toString().trim().isEmpty()) {
                this.phone = Integer.parseInt(phone.getText().toString());
            }else{
                this.phone = 0;
            }
            student.put("phone", this.phone);

            if (!zip_code.getText().toString().trim().isEmpty()) {
                this.zip_code = Integer.parseInt(zip_code.getText().toString());
                student.put("zip_code", this.zip_code);
            }

            if (!cpr.getText().toString().trim().isEmpty()) {
                this.cpr = Integer.parseInt(cpr.getText().toString());
            }else{
                this.cpr = 0;
            }
            student.put("cpr", this.cpr);


            DocumentReference studentRef = FirebaseFirestore.getInstance()
                    .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("student").document(studentId);

            studentRef.update(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressBar.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Updating student successfully", 0).show();
//                controller.navigate(action);
//                controller.navigateUp();
//                controller.popBackStack();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(activity, "Error updating student\n" + e.getMessage(), 1).show();
                    button.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

}
