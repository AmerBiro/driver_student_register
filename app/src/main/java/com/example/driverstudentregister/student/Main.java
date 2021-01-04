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
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.StudentMainBinding;
import com.example.driverstudentregister.home.HomeDirections;
import com.example.driverstudentregister.mvvm.StudentViewModel;

public class Main extends Fragment {


    private @NonNull StudentMainBinding binding;
    private NavController controller;
    private StudentViewModel studentViewModel;
    private String name, phone, street, zip_code, city, cpr, studentId, price, discount, note;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        name = MainArgs.fromBundle(getArguments()).getName();
        phone = MainArgs.fromBundle(getArguments()).getPhone();
        street = MainArgs.fromBundle(getArguments()).getStreet();
        zip_code = MainArgs.fromBundle(getArguments()).getZipCode();
        city = MainArgs.fromBundle(getArguments()).getCity();
        cpr = MainArgs.fromBundle(getArguments()).getCpr();
        studentId = MainArgs.fromBundle(getArguments()).getStudentId();
        price = MainArgs.fromBundle(getArguments()).getPrice();
        discount = MainArgs.fromBundle(getArguments()).getDiscount();
        note = MainArgs.fromBundle(getArguments()).getNote();
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.name.setText(name);

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDirections.ActionMainToViewPagerInfo action = MainDirections.actionMainToViewPagerInfo();
                action.setName(name);
                action.setPhone(phone);
                action.setStreet(street);
                action.setZipCode(zip_code);
                action.setCity(city);
                action.setCpr(cpr);
                action.setStudentId(studentId);
                action.setPrice(price);
                action.setDiscount(discount);
                controller.navigate(action);
            }
        });

        binding.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_main_to_payment2);
            }
        });

        binding.theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_main_to_theory2);
            }
        });

        binding.practise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_main_to_practise2);
            }
        });

        binding.note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDirections.ActionMainToNote2 action = MainDirections.actionMainToNote2();
                action.setNote(note);
                action.setName(name);
                action.setStudentId(studentId);
                controller.navigate(action);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), studentId, 0).show();
                controller.navigate(R.id.action_main_to_home2);
            }
        });

    }



}