package com.example.driverstudentregister.registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.driverstudentregister.R;
import com.example.driverstudentregister.databinding.RegistrationSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private NavController controller;
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
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.username.getText().toString();
                password = binding.password.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "A new user has been created successfully...", 0).show();
                            controller.navigate(R.id.action_sign_in_to_home2);
                        } else {
                            Toast.makeText(getContext(), "Failed creating an account..." + task.getException(), 1).show();
                        }
                    }
                });
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.username.getText().toString();
                password = binding.password.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Logged in...", 0).show();
                            controller.navigate(R.id.action_sign_in_to_home2);
                        } else {
                            Toast.makeText(getContext(), "Failed logging in..." + task.getException(), 1).show();
                        }
                    }
                });
            }
        });
    }
}