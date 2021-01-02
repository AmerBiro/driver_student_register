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

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.HomeCreateStudentBinding;
import com.example.driverstudentregister.mvvm.CreateStudent;

import java.text.SimpleDateFormat;


public class Create_Student extends Fragment {


    private @NonNull HomeCreateStudentBinding binding;
    NavController controller;
    private CreateStudent student;
    private String name, phone, street, zip_code, city, cpr, date;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                name = binding.name.getText().toString();
                phone = binding.phone.getText().toString();
                street = binding.street.getText().toString();
                zip_code = binding.zipCode.getText().toString();
                city = binding.city.getText().toString();
                cpr = binding.cpr.getText().toString();
                date = Double.toString(System.currentTimeMillis()/1000);

                String timeStamp = new SimpleDateFormat("dd/MM/yyyy\tHH:mm").format(Calendar.getInstance().getTime());

                student = new CreateStudent(
                        name, "name",
                        phone, "phone",
                        street, "street",
                        zip_code, "zip_code",
                        city, "city",
                        cpr, "cpr",
                        timeStamp, "date");

                student.createStudent();
                controller.navigate(R.id.action_create_Student_to_home2);
            }
        });
    }

}