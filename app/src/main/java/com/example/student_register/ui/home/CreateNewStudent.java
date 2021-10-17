package com.example.student_register.ui.home;

import com.example.student_register.R;
import com.example.student_register.databinding.HomeCreateStudentBinding;
import com.example.student_register.ui.student.edit.CreateStudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CreateNewStudent extends Fragment implements View.OnClickListener {

    // initializing Navigation and create a new student
    private @NonNull
    HomeCreateStudentBinding
     binding;
    private NavController controller;
    private CreateStudent student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeCreateStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setting up CreateStudent class and providing the necessary arguments
        controller = Navigation.findNavController(view);
        student = new CreateStudent(getActivity(), controller, view);

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_button:
                createStudent();
                break;
            default:
        }
    }

    private void createStudent(){
        // restricting the user from creating a new student if student name is empty
        if (binding.name.getText().toString().trim().isEmpty()){
            binding.name.setError("Name cannot be empty");
            return;
        }else{
            // using create function to create a new student
            student.create(
                    binding.name, binding.phone,
                    binding.street, binding.zipCode, binding.city,
                    binding.cpr,
                    binding.price, binding.discount,
                    binding.note,
                    binding.email,
                    binding.createButton, binding.progressBar,
                    R.id.action_create_Student_to_home
            );
        }
    }
}