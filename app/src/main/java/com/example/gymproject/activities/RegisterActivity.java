package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.example.gymproject.utilities.FullScreenManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextUsername, editTextEmail;
    private TextInputLayout editTextPassword, editTextConfirmPassword;
    private MaterialButton buttonRegister, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FullScreenManager.getInstance().fullScreen(getWindow());
        mAuth = FirebaseAuth.getInstance();
        initViews();
        initButtons();
    }

    private void initViews() {
        editTextUsername = findViewById(R.id.editTextRegisterUsername);
        editTextEmail = findViewById(R.id.editTextRegisterEmail);
        editTextPassword = findViewById(R.id.register_EDT_Password);
        editTextConfirmPassword = findViewById(R.id.register_EDT_Confirm_Password);
        buttonRegister = findViewById(R.id.buttonRegister);
        btnReturn = findViewById(R.id.btnReturnToVisitPage);
    }

    private void initButtons() {
        buttonRegister.setOnClickListener(v -> setupRegisterButton());
        btnReturn.setOnClickListener(v -> navigateToVisitPage());
    }

    private void setupRegisterButton() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getEditText().getText().toString().trim();

        if (validateInputs(username, email, password, confirmPassword)) {
            registerUser(email, password, username);
        }
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Valid email is required");
            editTextEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void registerUser(String email, String password, String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUserProfile(username);
                    } else {
                        handleRegistrationError(task.getException());
                    }
                });
    }

    private void updateUserProfile(String username) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(profileTask -> {
                        if (profileTask.isSuccessful()) {
                            navigateToPlansPage();
                        } else {
                            showToast("Profile update failed: " + profileTask.getException().getMessage());
                        }
                    });
        }
    }

    private void navigateToPlansPage() {
        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MyPlansActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleRegistrationError(Exception exception) {
        if (exception instanceof FirebaseAuthUserCollisionException) {
            showToast("User with this email already exists");
        } else {
            showToast("Registration failed: " + exception.getMessage());
        }
    }

    private void navigateToVisitPage() {
        Intent intent = new Intent(RegisterActivity.this, VisitPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
