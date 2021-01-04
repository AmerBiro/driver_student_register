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

import com.example.student_register.databinding.StudentPractiseBinding;
import com.example.student_register.functions.CustomDatePicker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Practise extends Fragment {

    private @NonNull StudentPractiseBinding binding;
    private String practise1, practise2, practise3, practise4, practise5, practise6, practise7, practise8, practise9, practise10;
    private String[] date;
    private TextView[] dateTextView;
    private CustomDatePicker datePicker;
    private NavController controller;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String userId, studentId;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentPractiseBinding.inflate(inflater, container, false);
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
        studentId = PractiseArgs.fromBundle(getArguments()).getStudentId();
        datePicker = new CustomDatePicker();
        date = new String[10];
        dateTextView = new TextView[10];
        date[0] = PractiseArgs.fromBundle(getArguments()).getPractise1();
        date[1] = PractiseArgs.fromBundle(getArguments()).getPractise2();
        date[2] = PractiseArgs.fromBundle(getArguments()).getPractise3();
        date[3] = PractiseArgs.fromBundle(getArguments()).getPractise4();
        date[4] = PractiseArgs.fromBundle(getArguments()).getPractise5();
        date[5] = PractiseArgs.fromBundle(getArguments()).getPractise6();
        date[6] = PractiseArgs.fromBundle(getArguments()).getPractise7();
        date[7] = PractiseArgs.fromBundle(getArguments()).getPractise8();
        date[8] = PractiseArgs.fromBundle(getArguments()).getPractise9();
        date[9] = PractiseArgs.fromBundle(getArguments()).getPractise10();

        dateTextView[0] = binding.practise01Date;
        dateTextView[1] = binding.practise02Date;
        dateTextView[2] = binding.practise03Date;
        dateTextView[3] = binding.practise04Date;
        dateTextView[4] = binding.practise05Date;
        dateTextView[5] = binding.practise06Date;
        dateTextView[6] = binding.practise07Date;
        dateTextView[7] = binding.practise08Date;
        dateTextView[8] = binding.practise09Date;
        dateTextView[9] = binding.practise10Date;

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

        binding.practise01Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise01Date);
            }
        });

        binding.practise02Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise02Date);
            }
        });

        binding.practise03Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise03Date);
            }
        });

        binding.practise04Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise04Date);
            }
        });

        binding.practise05Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise05Date);
            }
        });

        binding.practise06Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise06Date);
            }
        });

        binding.practise07Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise07Date);
            }
        });

        binding.practise08Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise08Date);
            }
        });

        binding.practise09Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise09Date);
            }
        });

        binding.practise10Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.datePicker(getActivity(), binding.practise10Date);
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
        updateDate.put("practise1", binding.practise01Date.getText().toString());
        updateDate.put("practise2", binding.practise02Date.getText().toString());
        updateDate.put("practise3", binding.practise03Date.getText().toString());
        updateDate.put("practise4", binding.practise04Date.getText().toString());
        updateDate.put("practise5", binding.practise05Date.getText().toString());
        updateDate.put("practise6", binding.practise06Date.getText().toString());
        updateDate.put("practise7", binding.practise07Date.getText().toString());
        updateDate.put("practise8", binding.practise08Date.getText().toString());
        updateDate.put("practise9", binding.practise09Date.getText().toString());
        updateDate.put("practise10", binding.practise10Date.getText().toString());

        Task<Void> documentReference = FirebaseFirestore
                .getInstance().collection("user")
                .document(userId).collection("student")
                .document(studentId).update(updateDate);

        PractiseDirections.ActionPractise2ToMain action = PractiseDirections.actionPractise2ToMain();
        action.setStudentId(studentId);
        action.setLecture1(dateTextView[0].getText().toString());
        action.setLecture2(dateTextView[1].getText().toString());
        action.setLecture3(dateTextView[2].getText().toString());
        action.setLecture4(dateTextView[3].getText().toString());
        action.setLecture5(dateTextView[4].getText().toString());
        action.setLecture6(dateTextView[5].getText().toString());
        action.setLecture7(dateTextView[6].getText().toString());
        action.setLecture8(dateTextView[7].getText().toString());
        action.setLecture8(dateTextView[8].getText().toString());
        action.setLecture8(dateTextView[9].getText().toString());
        controller.navigate(action);
        controller.navigateUp();
        controller.popBackStack();
    }

}