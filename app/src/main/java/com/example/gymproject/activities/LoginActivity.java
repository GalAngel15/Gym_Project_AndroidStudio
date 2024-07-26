package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextView checkTextView;
    private MaterialButton login_BTN_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButtons();
        mAuth = FirebaseAuth.getInstance();
        checkTextView = findViewById(R.id.checkTextView);
        validateLogin();

//        login_BTN_login.setOnClickListener(v -> {
//            login_BTN_login.setEnabled(false); // Disable button to prevent double click
//            String email = emailInputLayout.getEditText().getText().toString().trim();
//            String password = passwordInputLayout.getEditText().getText().toString().trim();
//            login();
//        });
        login_BTN_login.setOnClickListener(v -> {
            login_BTN_login.setEnabled(false); // Disable button to prevent double click
            String email = emailInputLayout.getEditText().getText().toString().trim();
            String password = passwordInputLayout.getEditText().getText().toString().trim();
            if (validateInputs(email, password)) {
                loginUser(email, password);
            } else {
                login_BTN_login.setEnabled(true); // Enable button if validation fails
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Valid email is required");
            emailInputLayout.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            passwordInputLayout.requestFocus();
            return false;
        }

        return true;
    }

    private void initButtons() {
        login_BTN_login = findViewById(R.id.buttonLoginLoginPage);
        emailInputLayout = findViewById(R.id.login_EDT_mail);
        passwordInputLayout = findViewById(R.id.login_EDT_Password);
        checkTextView = findViewById(R.id.checkTextView);
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    login_BTN_login.setEnabled(true);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            checkTextView.setText(
                                    user.getDisplayName() + "\n" +
                                            user.getUid() + "\n" +
                                            user.getProviderId() + "\n" +
                                            user.getEmail() + "\n" +
                                            user.getPhoneNumber()
                            );
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void login() {
//        Intent signInIntent = AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(Arrays.asList(
//                        new AuthUI.IdpConfig.EmailBuilder().build(),
//                        new AuthUI.IdpConfig.PhoneBuilder().build()
//                ))
//                .build();
//
//        signInLauncher.launch(signInIntent);
//    }

//    private ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
//            new FirebaseAuthUIActivityResultContract(),
//            (result) -> {
//                validateLogin();
//                login_BTN_login.setEnabled(true);
//            });

    private void validateLogin() {
        currentUser = mAuth.getCurrentUser(); //get current user
        if (currentUser != null) {
            checkTextView.setText(
                    currentUser.getDisplayName() + "\n" +
                            currentUser.getUid() + "\n" +
                            currentUser.getProviderId() + "\n" +
                            currentUser.getEmail() + "\n" +
                            currentUser.getPhoneNumber()
            );
        }else{
            checkTextView.setText("no user");
        }

    }
}
