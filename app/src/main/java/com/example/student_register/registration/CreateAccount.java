package com.example.student_register.registration;

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

import com.example.student_register.R;
import com.example.student_register.databinding.RegistrationCreateAccountBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CreateAccount extends Fragment {
    private NavController controller;
    private com.example.student_register.buttons.CreateAccount createAccountButton;
    private @NonNull RegistrationCreateAccountBinding binding;
    private String username;
    private String password;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationCreateAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        createAccountButton = new com.example.student_register.buttons.CreateAccount();
        createAccountButton.setView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        createAccountButton.setDefaultText("Create");
        createAccountButton.getView().setOnClickListener(v -> {
            username = binding.username.getText().toString();
            password = binding.password.getText().toString();
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                return;
            }else{
                createAccountButton.onClick("Please wait");
                new Handler().postDelayed(() -> FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        createAccountButton.onSuccess("Done");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Account created successfully ...", 0).show();
                                controller.navigate(R.id.action_createAccount_to_home2);
                            }
                        }, 1000);
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Error " + e.getMessage(), 1).show();
                    createAccountButton.onFailure("Error");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            createAccountButton.onRepeat("Create a new account");
                        }
                    }, 1000);
                }), 1500);
            }
        });
    }
}