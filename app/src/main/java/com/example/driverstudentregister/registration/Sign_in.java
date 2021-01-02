package com.example.driverstudentregister.registration;

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
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.RegistrationSignInBinding;
import com.example.driverstudentregister.buttons.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private NavController controller;
    private SignIn signInButton;
    private View customButtonSignIN;
    private String username;
    private String password;

    private @NonNull
    RegistrationSignInBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        controller = Navigation.findNavController(view);
        signInButton = new SignIn(view);
        customButtonSignIN = view.findViewById(R.id.custom_button_sign_in);
    }

    @Override
    public void onStart() {
        super.onStart();

        signInButton.setDefaultText("Sign in");

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_sign_in_to_createAccount);
            }
        });

        customButtonSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.username.getText().toString();
                password = binding.password.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                    return;
                }

                signInButton.onClick("Please wait...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    signInButton.onSuccess("Done");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Logged in...", 0).show();
                                            controller.navigate(R.id.action_sign_in_to_home2);
                                        }
                                    }, 1000);
                                } else {
                                    signInButton.onFailure("Error");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            signInButton.onRepeat("Sign in");
                                        }
                                    }, 1000);
                                }
                            }
                        });
                    }
                }, 1500);
            }
        });
    }
}