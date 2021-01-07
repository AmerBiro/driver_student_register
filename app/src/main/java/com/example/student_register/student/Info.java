package com.example.student_register.student;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.student_register.R;
import com.example.student_register.databinding.StudentInfoBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Info extends Fragment {

    private @NonNull StudentInfoBinding binding;
    private NavController controller;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String name, phone, street, zip_code, city, cpr, studentId, price, discount;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.name.setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        name = InfoArgs.fromBundle(getArguments()).getName();
        phone = InfoArgs.fromBundle(getArguments()).getPhone();
        street = InfoArgs.fromBundle(getArguments()).getStreet();
        zip_code = InfoArgs.fromBundle(getArguments()).getZipCode();
        city = InfoArgs.fromBundle(getArguments()).getCity();
        cpr = InfoArgs.fromBundle(getArguments()).getCpr();
        studentId = InfoArgs.fromBundle(getArguments()).getStudentId();
        price = InfoArgs.fromBundle(getArguments()).getPrice();
        discount = InfoArgs.fromBundle(getArguments()).getDiscount();
        controller = Navigation.findNavController(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.name.setText(name);
        binding.phone.setText(phone);
        binding.street.setText(street);
        binding.zipCode.setText(zip_code);
        binding.city.setText(city);
        binding.cpr.setText(cpr);
        binding.price.setText(price);
        binding.discount.setText(discount);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> student = new HashMap<>();

                student.put("name", binding.name.getText().toString());
                student.put("phone", binding.phone.getText().toString());
                student.put("street", binding.street.getText().toString());
                student.put("zip_code", binding.zipCode.getText().toString());
                student.put("city", binding.city.getText().toString());
                student.put("cpr", binding.cpr.getText().toString());
                student.put("price", binding.price.getText().toString());
                student.put("discount", binding.discount.getText().toString());

                Task<Void> documentReference = FirebaseFirestore
                        .getInstance().collection("user")
                        .document(firebaseUser.getUid()).collection("student")
                        .document(studentId).update(student);

                InfoDirections.ActionStudentInfoToMain3 action = InfoDirections.actionStudentInfoToMain3();
                action.setName(binding.name.getText().toString());
                action.setPhone(phone);
                action.setStreet(street);
                action.setZipCode(zip_code);
                action.setCity(city);
                action.setCpr(cpr);
                action.setStudentId(studentId);
                action.setPrice(price);
                action.setDiscount(discount);
                controller.navigate(action);
//                controller.navigateUp();
//                controller.popBackStack();
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete student");
                builder.setMessage("Are you sure that you want to delete the following student: " + name)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Task<Void> documentReference = FirebaseFirestore
                                        .getInstance().collection("user")
                                        .document(firebaseUser.getUid()).collection("student")
                                        .document(studentId).delete();
                                controller.navigate(R.id.action_student_info_to_home27);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}