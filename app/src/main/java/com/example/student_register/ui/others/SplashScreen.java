package com.example.student_register.ui.others;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.student_register.R;
import com.example.student_register.databinding.RegistrationSplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends Fragment {

    // initializing navgivation controller and binding the layout
    private NavController controller;
    private @NonNull
    RegistrationSplashScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setup NavController
        controller = Navigation.findNavController(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // setting a default text value
        binding.status.setText("Checking account info ...");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        signInAuto();
    }


    public void signInAuto(){
        // initializing a dealy handler to deal with account checking
        new Handler().postDelayed(() -> {
            // firebase instance and check whether an account is found or not
            // whether a user has been logged in yet
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                // if a user does not have an account, changing text value and transferring the user to sign up
                binding.status.setText("No account founded ...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        controller.navigate(R.id.action_splash_Screen_to_sign_in);
                    }
                },1000);
            }else{
                // if a user has an account, changing text value and transferring the user to home screen
                binding.status.setText("Logged in ...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        controller.navigate(R.id.action_splash_Screen_to_home2);
                    }
                },1000);
            }
        },1500);
    }

}