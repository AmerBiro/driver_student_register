package com.example.student_register.home;

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
import com.example.student_register.databinding.HomeSplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash_Screen extends Fragment {

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    NavController controller;

    private @NonNull HomeSplashScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        controller = Navigation.findNavController(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomeSplashScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.status.setText("Checking account info...");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        signInAuto();
    }


    public void signInAuto(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser == null){
                    binding.status.setText("No account founded...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            controller.navigate(R.id.action_splash_Screen_to_sign_in);
                        }
                    },1000);
                }else{
                    binding.status.setText("Logged in...");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            controller.navigate(R.id.action_splash_Screen_to_home2);
                        }
                    },1000);
                }
            }
        },2000);
    }

}