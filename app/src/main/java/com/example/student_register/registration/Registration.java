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
import com.example.student_register.databinding.RegistrationRegistrationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends Fragment implements View.OnClickListener {

    private NavController controller;
    private com.example.student_register.buttons.SignIn signInButton;
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
        controller = Navigation.findNavController(view);
        signInButton = new com.example.student_register.buttons.SignIn();
        signInButton.setView(view);
    }

    @Override
    public void onStart() {
        super.onStart();

        signInButton.setDefaultText("Sign in");

        binding.createAccount.setOnClickListener(this);

        signInButton.getView().setOnClickListener(v -> {
            username = binding.username.getText().toString();
            password = binding.password.getText().toString();
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                Toast.makeText(getActivity(), "username or password cannot be empty", 1).show();
                return;
            } else {
                signInButton.onClick("Please wait ...");
                new Handler().postDelayed(() -> FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        signInButton.onSuccess("Done");
                        new Handler().postDelayed(() -> {
                            Toast.makeText(getContext(), "Logged in ...", 0).show();
                            controller.navigate(R.id.action_sign_in_to_home2);
                        }, 1000);

                    }
                }).addOnFailureListener(e -> {
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
            case R.id.create_account:
                controller.navigate(R.id.action_sign_in_to_createAccount);
                break;
            default:
        }
    }
}