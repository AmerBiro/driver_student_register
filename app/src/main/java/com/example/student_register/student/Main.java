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

import com.example.student_register.databinding.StudentMainBinding;
import com.example.student_register.mvvm.StudentViewModel;

public class Main extends Fragment {


    private @NonNull StudentMainBinding binding;
    private NavController controller;
    private StudentViewModel studentViewModel;
    private String studentId;
    private String name, phone, street, zip_code, city, cpr;
    private String price, discount;
    private String lecture1, lecture2, lecture3, lecture4, lecture5, lecture6, lecture7, lecture8;
    private String practise1, practise2, practise3, practise4, practise5, practise6, practise7, practise8, practise9, practise10;
    private String note;

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

        studentId = MainArgs.fromBundle(getArguments()).getStudentId();

        name = MainArgs.fromBundle(getArguments()).getName();
        phone = MainArgs.fromBundle(getArguments()).getPhone();
        street = MainArgs.fromBundle(getArguments()).getStreet();
        zip_code = MainArgs.fromBundle(getArguments()).getZipCode();
        city = MainArgs.fromBundle(getArguments()).getCity();
        cpr = MainArgs.fromBundle(getArguments()).getCpr();

        price = MainArgs.fromBundle(getArguments()).getPrice();
        discount = MainArgs.fromBundle(getArguments()).getDiscount();

        lecture1 = MainArgs.fromBundle(getArguments()).getLecture1();
        lecture2 = MainArgs.fromBundle(getArguments()).getLecture2();
        lecture3 = MainArgs.fromBundle(getArguments()).getLecture3();
        lecture4 = MainArgs.fromBundle(getArguments()).getLecture4();
        lecture5 = MainArgs.fromBundle(getArguments()).getLecture5();
        lecture6 = MainArgs.fromBundle(getArguments()).getLecture6();
        lecture7 = MainArgs.fromBundle(getArguments()).getLecture7();
        lecture8 = MainArgs.fromBundle(getArguments()).getLecture8();

        practise1 = MainArgs.fromBundle(getArguments()).getPractise1();
        practise2 = MainArgs.fromBundle(getArguments()).getPractise2();
        practise3 = MainArgs.fromBundle(getArguments()).getPractise3();
        practise4 = MainArgs.fromBundle(getArguments()).getPractise4();
        practise5 = MainArgs.fromBundle(getArguments()).getPractise5();
        practise6 = MainArgs.fromBundle(getArguments()).getPractise6();
        practise7 = MainArgs.fromBundle(getArguments()).getPractise7();
        practise8 = MainArgs.fromBundle(getArguments()).getPractise8();
        practise9 = MainArgs.fromBundle(getArguments()).getPractise9();
        practise10 = MainArgs.fromBundle(getArguments()).getPractise10();

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
                MainDirections.ActionMainToPayment2 action = MainDirections.actionMainToPayment2();
                action.setStudentId(studentId);
                action.setPrice(price);
                action.setDiscount(discount);
                controller.navigate(action);
            }
        });

        binding.theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDirections.ActionMainToTheory2 action = MainDirections.actionMainToTheory2();
                action.setStudentId(studentId);
                action.setLecture1(lecture1);
                action.setLecture2(lecture2);
                action.setLecture3(lecture3);
                action.setLecture4(lecture4);
                action.setLecture5(lecture5);
                action.setLecture6(lecture6);
                action.setLecture7(lecture7);
                action.setLecture8(lecture8);
                controller.navigate(action);
            }
        });

        binding.practise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDirections.ActionMainToPractise2 action = MainDirections.actionMainToPractise2();

                action.setStudentId(studentId);

                action.setPractise1(practise1);
                action.setPractise2(practise2);
                action.setPractise3(practise3);
                action.setPractise4(practise4);
                action.setPractise5(practise5);
                action.setPractise6(practise6);
                action.setPractise7(practise7);
                action.setPractise8(practise8);
                action.setPractise9(practise9);
                action.setPractise10(practise10);

                controller.navigate(action);
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


    }



}