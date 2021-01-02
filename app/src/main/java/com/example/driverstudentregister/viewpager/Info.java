package com.example.driverstudentregister.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.ViewpagerInfoBinding;

import static android.content.ContentValues.TAG;


public class Info extends Fragment {

    private NavController controller;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        int position = InfoArgs.fromBundle(getArguments()).getPosition();
        Log.d(TAG, "Position: " + position);
    }
}