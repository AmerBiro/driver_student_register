package com.example.student_register.viewpager;

import com.example.student_register.databinding.ViewpagerPractiseBinding;
import com.example.student_register.mvvm.adapter.PractiseAdapter;
import com.example.student_register.mvvm.model.PractiseModel;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.DatePickerDialog;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Practise extends Fragment implements PractiseAdapter.OnPractiseItemClicked {

    private @NonNull
    ViewpagerPractiseBinding
            binding;
    private NavController controller;
    private int getAdapterPosition;
    private StudentViewModel studentViewModel;
    private List<PractiseModel> practiseModels = new ArrayList<>();
    private PractiseAdapter adapter;
    private RecyclerView recyclerView;
    private String studentId;
    private int years, months, days;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerPractiseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new PractiseAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void getPosition(int position) {
        getAdapterPosition = position;
        Log.d(TAG, "onViewCreated: " + "Practise: " + getAdapterPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentId = studentModels.get(getAdapterPosition).getStudentId();

                Query theoryRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("student").document(studentId)
                        .collection("practise").orderBy("number");
                theoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        practiseModels = value.toObjects(PractiseModel.class);
                        adapter.setPractiseModels(practiseModels);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onDateItemClicked(int position, EditText editText) {
        Calendar calendar = Calendar.getInstance();
        years = calendar.get(Calendar.YEAR);
        months = calendar.get(Calendar.MONTH);
        days = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), (view1, year, month, dayOfMonth) -> {
            months = month + 1;
            days = dayOfMonth;
            years = year;
            date = days + "/" + months + "/" + years;
            Log.d(TAG, "onDateItemClicked: " + date);
            editText.setText(date);

            DocumentReference theoryRef = FirebaseFirestore.getInstance()
                    .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("student").document(studentId)
                    .collection("practise").document(practiseModels.get(position).getPractiseId());

            HashMap<String, Object> theory = new HashMap<>();
            theory.put("date", date);

            theoryRef.update(theory).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Updated successfully", 0).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error", 0).show();
                }
            });


        }, years, months, days);
        datePickerDialog.show();

    }

}