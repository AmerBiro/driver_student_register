package com.example.student_register.viewpager;

import com.example.student_register.databinding.ViewpagerTheoryBinding;
import com.example.student_register.mvvm.adapter.PaymentAdapter;
import com.example.student_register.mvvm.adapter.TheoryAdapter;
import com.example.student_register.mvvm.model.PaymentModel;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.model.TheoryModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.example.student_register.student.AddPayment;
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
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Theory extends Fragment implements TheoryAdapter.OnTheoryItemClicked {

    private @NonNull
    ViewpagerTheoryBinding
            binding;
    private NavController controller;
    private int getAdapterPosition;
    private StudentViewModel studentViewModel;
    private List<TheoryModel> theoryModels = new ArrayList<>();
    private TheoryAdapter adapter;
    private RecyclerView recyclerView;
    private String studentId;
    private int years, months, days;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerTheoryBinding.inflate(inflater, container, false);
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
        adapter = new TheoryAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void getPosition(int position) {
        getAdapterPosition = position;
        Log.d(TAG, "onViewCreated: " + "Theory: " + getAdapterPosition);
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
                        .collection("theory").orderBy("number");
                theoryRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        theoryModels = value.toObjects(TheoryModel.class);
                        adapter.setTheoryModels(theoryModels);
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
                    .collection("theory").document(theoryModels.get(position).getTheoryId());

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