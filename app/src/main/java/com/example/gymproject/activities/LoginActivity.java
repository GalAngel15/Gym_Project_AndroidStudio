package com.example.gymproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymproject.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextView checkTextView;
    private MaterialButton login_BTN_login, btnReturn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        initViews();
        initButtons();
    }

    private void initViews() {
        login_BTN_login = findViewById(R.id.buttonLoginLoginPage);
        emailInputLayout = findViewById(R.id.login_EDT_mail);
        passwordInputLayout = findViewById(R.id.login_EDT_Password);
        checkTextView = findViewById(R.id.checkTextView);
        progressBar = findViewById(R.id.progressBar);
        btnReturn = findViewById(R.id.btnReturnToVisitPage);
    }

    private void initButtons() {
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
        btnReturn.setOnClickListener(v -> navigateToVisitPage());
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

    private void loginUser(String email, String password) {
        showProgress(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    login_BTN_login.setEnabled(true);
                    showProgress(false);

                    if (task.isSuccessful()) {
                        onLoginSuccess();
                    } else {
                        onLoginFailed();
                    }
                });
    }
    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void onLoginSuccess() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            checkTextView.setVisibility(TextView.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            navigateToMyPlans();
        }
    }

    private void onLoginFailed() {
        checkTextView.setText(getString(R.string.error_incorrect_login_fields));
        checkTextView.setVisibility(TextView.VISIBLE);
        checkTextView.setTextColor(getResources().getColor(R.color.red, null));
    }

    private void navigateToMyPlans() {
        Intent intent = new Intent(LoginActivity.this, MyPlansActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the activity stack
        startActivity(intent);
        finish();
    }

    private void navigateToVisitPage() {
        Intent intent = new Intent(LoginActivity.this, VisitPageActivity.class);
        startActivity(intent);
        finish();
    }
}
