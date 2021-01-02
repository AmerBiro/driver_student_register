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
import com.example.driverstudentregister.databinding.RegistrationCreateAccountBinding;
import com.example.driverstudentregister.functions.CustomButtonCreateAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CreateAccount extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private NavController controller;
    private CustomButtonCreateAccount createAccountButton;
    private View customButtonCreateAccount;
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
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        controller = Navigation.findNavController(view);
        createAccountButton = new CustomButtonCreateAccount(view);
        customButtonCreateAccount = view.findViewById(R.id.custom_button_create_account);

    }

    @Override
    public void onStart() {
        super.onStart();
        createAccountButton.setDefaultText("Create");


        customButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.username.getText().toString();
                password = binding.password.getText().toString();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                    return;
                }

                createAccountButton.onClick("Please wait...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    createAccountButton.onSuccess("Done");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "Account created successfully...", 0).show();
                                            controller.navigate(R.id.action_createAccount_to_home2);
                                        }
                                    }, 1000);
                                } else {
                                    createAccountButton.onFailure("Error");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            createAccountButton.onRepeat("Create a new account");
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