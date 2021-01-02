package com.example.driverstudentregister.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.HomeHomeBinding;
import com.example.driverstudentregister.mvvm.StudentAdapter;
import com.example.driverstudentregister.mvvm.StudentModel;
import com.example.driverstudentregister.mvvm.StudentViewModel;

import java.util.List;


public class Home extends Fragment {

    private @NonNull HomeHomeBinding binding;

    private NavController controller;
    private RecyclerView recyclerView;
    private StudentViewModel studentViewModel;
    private StudentAdapter adapter;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerView = binding.recyclerview;
        adapter = new StudentAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_home2_to_create_Student);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                adapter.setStudentModels(studentModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

}