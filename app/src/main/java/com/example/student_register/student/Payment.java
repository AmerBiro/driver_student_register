package com.example.student_register.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.student_register.databinding.StudentPaymentBinding;

public class Payment extends Fragment {

    private @NonNull StudentPaymentBinding binding;
    private String price, discount, total_price;
    private int priceInt, discountInt;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentPaymentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.price.setEnabled(false);
        binding.discount.setEnabled(false);
        binding.totalPrice.setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        price = PaymentArgs.fromBundle(getArguments()).getPrice();
        discount = PaymentArgs.fromBundle(getArguments()).getDiscount();

        priceInt = Integer.parseInt(price);
        discountInt = Integer.parseInt(discount);
        total_price = priceInt - discountInt + "";
    }

    @Override
    public void onStart() {
        super.onStart();

        binding.price.setText(price);
        binding.discount.setText(discount);
        binding.totalPrice.setText(total_price);

        binding.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "100", 0).show();
            }
        });

    }
}