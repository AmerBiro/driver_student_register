package com.example.driverstudentregister.viewpager;

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

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.ViewpagerInfoBinding;
import com.example.driverstudentregister.mvvm.StudentViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Info extends Fragment {

    private @NonNull ViewpagerInfoBinding binding;
    private NavController controller;
    private StudentViewModel studentViewModel;
    private String studentId, name, phone, street, zip_code, city, cpr;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.name.setEnabled(false);
        binding.phone.setEnabled(false);
        binding.street.setEnabled(false);
        binding.zipCode.setEnabled(false);
        binding.city.setEnabled(false);
        binding.cpr.setEnabled(false);

        name = InfoArgs.fromBundle(getArguments()).getName();
        phone = InfoArgs.fromBundle(getArguments()).getPhone();
        street = InfoArgs.fromBundle(getArguments()).getStreet();
        zip_code = InfoArgs.fromBundle(getArguments()).getZipCode();
        city = InfoArgs.fromBundle(getArguments()).getCity();
        cpr = InfoArgs.fromBundle(getArguments()).getCpr();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        studentId = InfoArgs.fromBundle(getArguments()).getStudentId();
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

        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.name.setEnabled(true);
                binding.phone.setEnabled(true);
                binding.street.setEnabled(true);
                binding.zipCode.setEnabled(true);
                binding.city.setEnabled(true);
                binding.cpr.setEnabled(true);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                Map<String, Object> student = new HashMap<>();

                student.put("name", binding.name.getText().toString());
                student.put("phone", binding.phone.getText().toString());
                student.put("street", binding.street.getText().toString());
                student.put("zip_code", binding.zipCode.getText().toString());
                student.put("city", binding.city.getText().toString());
                student.put("cpr", binding.cpr.getText().toString());

                Task<Void> documentReference = FirebaseFirestore
                        .getInstance().collection("user")
                        .document(firebaseUser.getUid()).collection("student")
                        .document(studentId).update(student);
                controller.navigate(R.id.action_view_pager_info_to_home2);
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete student");
                builder.setMessage("Are you sure that you want to delete the following student: " + name)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                Task<Void> documentReference = FirebaseFirestore
                                        .getInstance().collection("user")
                                        .document(firebaseUser.getUid()).collection("student")
                                        .document(studentId).delete();
                                controller.navigate(R.id.action_view_pager_info_to_home2);
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