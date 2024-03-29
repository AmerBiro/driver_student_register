package com.example.student_register.data.mvvm;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.student_register.data.mvvm.model.StudentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;


public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Query studentRef = FirebaseFirestore.getInstance()
            .collection("user").document(userId)
            .collection("student")
            .orderBy("date");

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getStudentData() {
        studentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<StudentModel> studentModels = value.toObjects(StudentModel.class);
                Log.d(TAG, "onEvent: " + value.getDocumentChanges());
                onFirestoreTaskComplete.studentDataAdded(studentModels);
            }
        });


    }

    public interface OnFirestoreTaskComplete {
        void studentDataAdded(List<StudentModel> studentModels);

        void onError(Exception e);
    }

}
