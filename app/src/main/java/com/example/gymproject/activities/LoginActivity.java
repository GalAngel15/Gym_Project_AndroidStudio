package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView textView;
    private MaterialButton login_BTN_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_BTN_login = findViewById(R.id.buttonLoginLoginPage);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.checkTextView);
        validateLogin();
        login_BTN_login.setOnClickListener(v -> {
            login_BTN_login.setEnabled(false); // Disable button to prevent double click
            login();
        });
        validateLogin();

    }

    private void login() {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build()
                ))
                .build();

        signInLauncher.launch(signInIntent);
    }

    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            (result) -> {
                validateLogin();
                login_BTN_login.setEnabled(true);
            });

    private void validateLogin() {
        FirebaseUser currentUser = mAuth.getCurrentUser(); //get current user
        if (currentUser != null) {
            textView.setText(
                    currentUser.getDisplayName() + "\n" +
                            currentUser.getUid() + "\n" +
                            currentUser.getProviderId() + "\n" +
                            currentUser.getEmail() + "\n" +
                            currentUser.getPhoneNumber()
            );
        }else{
            textView.setText("no user");
        }

    }
}
