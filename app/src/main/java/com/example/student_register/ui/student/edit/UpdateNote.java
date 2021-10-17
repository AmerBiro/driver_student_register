package com.example.student_register.ui.student.edit;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UpdateNote {

    private String studentId, note;
    private Button button;
    private ProgressBar progressBar;

    public void updateNote(String studentId, EditText note, Button button, ProgressBar progressBar, Activity activity) {
        button.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        this.studentId = studentId;
        this.note = note.getText().toString();
        this.button = button;
        this.progressBar = progressBar;

        DocumentReference noteRef = FirebaseFirestore.getInstance()
                .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("student").document(this.studentId);

        HashMap<String, Object> noteMap = new HashMap<>();
        noteMap.put("note", this.note);

        noteRef.update(noteMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "Updated successfully", 0).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(activity, "Error", 0).show();
            }
        });


    }
}
