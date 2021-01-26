package com.example.student_register.viewpager;


import com.example.student_register.databinding.ViewpagerPaymentBinding;
import com.example.student_register.mvvm.adapter.PaymentAdapter;
import com.example.student_register.mvvm.adapter.StudentAdapter;
import com.example.student_register.mvvm.model.PaymentModel;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Payment extends Fragment implements PaymentAdapter.OnPaymentItemClicked {

    private @NonNull
    ViewpagerPaymentBinding
            binding;
    private NavController controller;
    private int getAdapterPosition;
    private StudentViewModel studentViewModel;
    private List<PaymentModel> paymentModels = new ArrayList<>();
    private PaymentAdapter adapter;
    private RecyclerView recyclerView;
    private String studentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerPaymentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        studentViewModel.getStudentModelData().observe(getViewLifecycleOwner(), new Observer<List<StudentModel>>() {
            @Override
            public void onChanged(List<StudentModel> studentModels) {
                studentId = studentModels.get(getAdapterPosition).getStudentId();
                int price, discount;
                price = studentModels.get(getAdapterPosition).getPrice();
                discount = studentModels.get(getAdapterPosition).getDiscount();

                binding.price.setText(price + " DKK");
                binding.discount.setText(discount + " DKK");

                Query paymentRef = FirebaseFirestore.getInstance()
                        .collection("user").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("student").document(studentId)
                        .collection("payment").orderBy("date");
                paymentRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        paymentModels = value.toObjects(PaymentModel.class);
                        adapter.setPaymentModels(paymentModels);
                        adapter.notifyDataSetChanged();

                        int total = 0;
                        for (int i = 0; i < paymentModels.size(); i++) {
                            total = total + paymentModels.get(i).getPayment();
                        }
                        Log.d(TAG, "onEvent: " + total);
                        binding.totalPrice.setText((price - discount - total) + " DKK");

                    }
                });


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getPosition(int position) {
        getAdapterPosition = position;
        Log.d(TAG, "onViewCreated: " + "Payment: " + getAdapterPosition);
    }


    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new PaymentAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClicked(int position) {

    }
}