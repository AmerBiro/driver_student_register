package com.example.student_register.viewpager;


import com.example.student_register.R;
import com.example.student_register.databinding.ViewpagerPaymentBinding;
import com.example.student_register.mvvm.adapter.PaymentAdapter;
import com.example.student_register.mvvm.adapter.StudentAdapter;
import com.example.student_register.mvvm.model.PaymentModel;
import com.example.student_register.mvvm.model.StudentModel;
import com.example.student_register.mvvm.viewmodel.StudentViewModel;
import com.example.student_register.student.AddPayment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.DialogInterface;
import android.graphics.Color;
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


public class Payment extends Fragment implements PaymentAdapter.OnPaymentItemClicked, View.OnClickListener {

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
    private AddPayment addPayment;
    private int packagePrice, totalPrice, discount, payments;

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
        binding.price.setEnabled(false);
        binding.discount.setEnabled(false);
        binding.totalPrice.setEnabled(false);
        addPayment = new AddPayment();
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

                        packagePrice = studentModels.get(getAdapterPosition).getPrice();
                        discount = studentModels.get(getAdapterPosition).getDiscount();
                        payments = 0;
                        for (int i = 0; i < paymentModels.size(); i++) {
                            payments = payments + paymentModels.get(i).getPayment();
                        }

                        binding.price.setText(packagePrice + " DKK");
                        binding.discount.setText(discount + " DKK");

                        totalPrice = packagePrice - payments - discount;
                        binding.totalPrice.setText(totalPrice + " DKK");
                        if (totalPrice > 0){
                            binding.totalPrice.setTextColor(Color.RED);
                        }else{
                            binding.totalPrice.setTextColor(Color.GREEN);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.floatingButtonPayment.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_button_payment:
                Log.d(TAG, "onClick: " + studentId + ", " + totalPrice);
                addPayment.addSinglePayment(getActivity(), studentId, totalPrice);
                break;
            default:
        }
    }

}