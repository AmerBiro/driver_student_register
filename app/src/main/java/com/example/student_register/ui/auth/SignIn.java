package com.example.student_register.ui.auth;

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
import com.example.student_register.databinding.RegistrationRegistrationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends Fragment implements View.OnClickListener {

    // initializing NavController, home made sign in buttons and a username and a password
    private NavController controller;
    private com.example.student_register.ui.others.buttons.SignIn signInButton;
    private String username;
    private String password;

    private @NonNull
    RegistrationRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RegistrationRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setting up NavController and sign in button
        controller = Navigation.findNavController(view);
        signInButton = new com.example.student_register.ui.others.buttons.SignIn();
        signInButton.setView(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        // default text value
        signInButton.setDefaultText("Sign in");
        binding.createAccount.setOnClickListener(this);

        // onClick sign in button
        signInButton.getView().setOnClickListener(v -> {
            // getting username and password values from layout
            username = binding.username.getText().toString();
            password = binding.password.getText().toString();
            // checking whether a username or a password is empty and stop the process if so
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                return;
            } else {
                // if not, set text value to following and start processing logging in
                signInButton.onClick("Please wait");
                new Handler().postDelayed(() -> FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // if sign in successes, chang text value and transfer the user to home screen
                        signInButton.onSuccess("Done");
                        new Handler().postDelayed(() -> {
                            Toast.makeText(getContext(), "Logged in ...", 0).show();
                            controller.navigate(R.id.action_sign_in_to_home2);
                        }, 1000);

                    }
                }).addOnFailureListener(e -> {
                    // if sign in fails, return an error message
                    signInButton.onFailure("Error");
                    Toast.makeText(getActivity(),"Error " +  e.getMessage(), 1).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            signInButton.onRepeat("Sign in");
                        }
                    }, 1000);
                }), 1500);
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // transfer the user to sign up screen
            case R.id.create_account:
                controller.navigate(R.id.action_sign_in_to_createAccount);
                break;
            default:
        }
    }
}