package com.example.driverstudentregister.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.StudentTheoryBinding;

public class Theory extends Fragment {

    private @NonNull
    StudentTheoryBinding binding;
    private String lecture1, lecture2, lecture3, lecture4, lecture5, lecture6, lecture7, lecture8;
    private String[] date;
    private TextView[] dateTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentTheoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

            }
        });


    }
}