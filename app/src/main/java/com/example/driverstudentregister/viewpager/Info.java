package com.example.driverstudentregister.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.ViewpagerInfoBinding;


public class Info extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private @NonNull ViewpagerInfoBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewpagerInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.name.setEnabled(false);
        binding.phone.setEnabled(false);
        binding.address.setEnabled(false);
        binding.post.setEnabled(false);
        binding.cpr.setEnabled(false);

        return view;
    }
}