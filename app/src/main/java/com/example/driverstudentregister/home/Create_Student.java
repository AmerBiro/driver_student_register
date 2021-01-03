package com.example.driverstudentregister.home;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.HomeCreateStudentBinding;
import com.example.driverstudentregister.functions.FieldChecker;
import com.example.driverstudentregister.mvvm.CreateStudent;
import com.google.common.collect.MapMaker;

import java.text.SimpleDateFormat;


public class Create_Student extends Fragment {


    private @NonNull
    HomeCreateStudentBinding binding;
    NavController controller;
    private CreateStudent student;
    private String name, phone, street, zip_code, city, cpr, date;
    private FieldChecker checker;
    private EditText[] field;
    private String[] errorMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeCreateStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = new FieldChecker();
                field = new EditText[6];
                errorMessage = new String[6];
                field[0] = binding.name;
                field[1] = binding.phone;
                field[2] = binding.street;
                field[3] = binding.zipCode;
                field[4] = binding.city;
                field[5] = binding.cpr;

                errorMessage[0] = "Select a name";
                errorMessage[1] = "Select a phone number";
                errorMessage[2] = "Select a street";
                errorMessage[3] = "Select a zip code";
                errorMessage[4] = "Select a city";
                errorMessage[5] = "Select a cpr number";

                date = new SimpleDateFormat("dd/MM/yyyy\tHH:mm").format(Calendar.getInstance().getTime());

//                if (!checker.isEmpty(field, errorMessage)){
//                    student = new CreateStudent(
//                            field[0].getText().toString(), "name",
//                            field[1].getText().toString(), "phone",
//                            field[2].getText().toString(), "street",
//                            field[3].getText().toString(), "zip_code",
//                            field[4].getText().toString(), "city",
//                            field[5].getText().toString(), "cpr",
//                            date, "date");
//                }
//                else{
//                    Toast.makeText(getActivity(), "Error creating student", 0).show();
//                    return;
//                }

                student = new CreateStudent(
                        field[0].getText().toString(), "name",
                        field[1].getText().toString(), "phone",
                        field[2].getText().toString(), "street",
                        field[3].getText().toString(), "zip_code",
                        field[4].getText().toString(), "city",
                        field[5].getText().toString(), "cpr",
                        date, "date");

                student.createStudent();
                controller.navigate(R.id.action_create_Student_to_home2);
            }
        });
    }

}