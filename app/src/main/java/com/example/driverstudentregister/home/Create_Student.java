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
    private String price, discount;
    private String note;

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
        student = new CreateStudent();
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
                date = new SimpleDateFormat("dd/MM/yyyy\tHH:mm").format(Calendar.getInstance().getTime());

                price = binding.price.getText().toString();
                discount = binding.discount.getText().toString();

                if (name.trim().isEmpty()){
                    binding.name.setError("A student name cannot be empty!");
                    return;
                }

                if (price.trim().isEmpty())
                    price = "13500";
                if (discount.trim().isEmpty())
                    discount = "0";

                student.setInfo(name, phone, street, zip_code, city, cpr, date);
                student.setPrice(price, discount);
                student.setNote("Add a note to " + name);

                student.createStudent();
                controller.navigate(R.id.action_create_Student_to_home2);
            }
        });
    }

}