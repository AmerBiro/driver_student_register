package com.example.student_register.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.student_register.databinding.StudentTheoryBinding;
import com.example.student_register.functions.CustomDatePicker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Theory extends Fragment   {

    private @NonNull
    StudentTheoryBinding binding;
    private String lecture1, lecture2, lecture3, lecture4, lecture5, lecture6, lecture7, lecture8;
    private String[] date;
    private TextView[] dateTextView;
    private CustomDatePicker datePicker;
    private NavController controller;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String userId, studentId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentTheoryBinding.inflate(inflater, container, false);
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
        studentId = TheoryArgs.fromBundle(getArguments()).getStudentId();
        datePicker = new CustomDatePicker();
        date = new String[8];
        dateTextView = new TextView[8];
        date[0] = TheoryArgs.fromBundle(getArguments()).getLecture1();
        date[1] = TheoryArgs.fromBundle(getArguments()).getLecture2();
        date[2] = TheoryArgs.fromBundle(getArguments()).getLecture3();
        date[3] = TheoryArgs.fromBundle(getArguments()).getLecture4();
        date[4] = TheoryArgs.fromBundle(getArguments()).getLecture5();
        date[5] = TheoryArgs.fromBundle(getArguments()).getLecture6();
        date[6] = TheoryArgs.fromBundle(getArguments()).getLecture7();
        date[7] = TheoryArgs.fromBundle(getArguments()).getLecture8();

        dateTextView[0] = binding.lecture01Date;
        dateTextView[1] = binding.lecture02Date;
        dateTextView[2] = binding.lecture03Date;
        dateTextView[3] = binding.lecture04Date;
        dateTextView[4] = binding.lecture05Date;
        dateTextView[5] = binding.lecture06Date;
        dateTextView[6] = binding.lecture07Date;
        dateTextView[7] = binding.lecture08Date;

    }

    @Override
    public void onStart() {
        super.onStart();

        for (int i = 0; i<dateTextView.length; i++){
            if (date[i].isEmpty()) {
                dateTextView[i].setText("dd/mm/yyyy");
            }else{
                dateTextView[i].setText(date[i]);
            }
        }

        binding.lecture01Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture01Date);
            }
        });

        binding.lecture02Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture02Date);
            }
        });

        binding.lecture03Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture03Date);
            }
        });

        binding.lecture04Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture04Date);
            }
        });

        binding.lecture05Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture05Date);
            }
        });

        binding.lecture06Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture06Date);
            }
        });

        binding.lecture07Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture07Date);
            }
        });

        binding.lecture08Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.lecture08Date);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });


    }

    private void updateDate() {
        Map<String, Object> updateDate = new HashMap<>();
        updateDate.put("lecture1", binding.lecture01Date.getText().toString());
        updateDate.put("lecture2", binding.lecture02Date.getText().toString());
        updateDate.put("lecture3", binding.lecture03Date.getText().toString());
        updateDate.put("lecture4", binding.lecture04Date.getText().toString());
        updateDate.put("lecture5", binding.lecture05Date.getText().toString());
        updateDate.put("lecture6", binding.lecture06Date.getText().toString());
        updateDate.put("lecture7", binding.lecture07Date.getText().toString());
        updateDate.put("lecture8", binding.lecture08Date.getText().toString());

        Task<Void> documentReference = FirebaseFirestore
                .getInstance().collection("user")
                .document(userId).collection("student")
                .document(studentId).update(updateDate);

        TheoryDirections.ActionTheory2ToMain action = TheoryDirections.actionTheory2ToMain();
        action.setStudentId(studentId);
        action.setLecture1(dateTextView[0].getText().toString());
        action.setLecture2(dateTextView[1].getText().toString());
        action.setLecture3(dateTextView[2].getText().toString());
        action.setLecture4(dateTextView[3].getText().toString());
        action.setLecture5(dateTextView[4].getText().toString());
        action.setLecture6(dateTextView[5].getText().toString());
        action.setLecture7(dateTextView[6].getText().toString());
        action.setLecture8(dateTextView[7].getText().toString());
        controller.navigate(action);
        controller.navigateUp();
        controller.popBackStack();
    }

}