package com.example.driverstudentregister.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.StudentNoteBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Note extends Fragment {

    private @NonNull StudentNoteBinding binding;
    private NavController controller;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String name, note, userId, studentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentNoteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        name = NoteArgs.fromBundle(getArguments()).getName();
        note = NoteArgs.fromBundle(getArguments()).getNote();
        studentId = NoteArgs.fromBundle(getArguments()).getStudentId();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (note.length() == 0){
            binding.note.setHint("Add a new note to " + name);
        }else{
            binding.note.setText(note);
        }

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });
    }

    private void updateNote() {
                Map<String, Object> noteUpdate = new HashMap<>();
                noteUpdate.put("note", binding.note.getText().toString());
                Task<Void> documentReference = FirebaseFirestore
                        .getInstance().collection("user")
                        .document(userId).collection("student")
                        .document(studentId).update(noteUpdate);

                NoteDirections.ActionNote2ToMain action = NoteDirections.actionNote2ToMain();
                action.setNote(note);
                action.setStudentId(studentId);
                controller.navigate(action);
                controller.navigateUp();
                controller.popBackStack();
    }
}