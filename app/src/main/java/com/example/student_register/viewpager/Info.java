package com.example.student_register.viewpager;

import com.example.student_register.R;
import com.example.student_register.databinding.ViewpagerInfoBinding;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.example.student_register.student.EditStudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static android.content.ContentValues.TAG;


public class Info extends Fragment implements View.OnClickListener {

    private @NonNull
    ViewpagerInfoBinding
     binding;
    private NavController controller;
    private int getAdapterPosition;
    private StudentViewModel studentViewModel;
    private EditStudent editStudent;
    private String studentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        editStudent = new EditStudent(getActivity(), controller, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentId = studentModels.get(getAdapterPosition).getStudentId();
                binding.name.setText(studentModels.get(getAdapterPosition).getName());
                binding.email.setText(studentModels.get(getAdapterPosition).getEmail());
                if (studentModels.get(getAdapterPosition).getPhone() == 0){
                    binding.phone.setText("");
                }else{
                    binding.phone.setText(studentModels.get(getAdapterPosition).getPhone() + "");
                }

                binding.street.setText(studentModels.get(getAdapterPosition).getStreet());

                if (studentModels.get(getAdapterPosition).getZip_code() == 0){
                    binding.zipCode.setText("");
                }else{
                    binding.zipCode.setText(studentModels.get(getAdapterPosition).getZip_code() + "");
                }

                binding.city.setText(studentModels.get(getAdapterPosition).getCity());

                if (studentModels.get(getAdapterPosition).getCpr() == 0){
                    binding.cpr.setText("");
                }else{
                    binding.cpr.setText(studentModels.get(getAdapterPosition).getCpr() + "");
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.updateButton.setOnClickListener(this);
    }

    public void getPosition(int position){
        getAdapterPosition = position;
        Log.d(TAG, "onViewCreated: " + "Info: " +  getAdapterPosition);
    }

    private void updateStudent(){
        if (binding.name.getText().toString().trim().isEmpty()){
            binding.name.setError("Name cannot be empty");
            return;
        }else{
            editStudent.editStudent(
                    studentId,
                    binding.name, binding.phone,
                    binding.street, binding.zipCode, binding.city,
                    binding.cpr,
                    binding.email,
                    binding.updateButton, binding.progressBar,
                    R.id.action_studentViewPager_to_home2
            );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_button:
                updateStudent();
                break;
            default:
        }
    }
}