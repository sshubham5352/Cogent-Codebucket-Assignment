package com.example.cogentcodebucketassignment.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.databinding.ActivityLoginBinding;
import com.example.cogentcodebucketassignment.firestore.FirestoreDbManager;
import com.example.cogentcodebucketassignment.firestore.FirestoreResponseListener;
import com.example.cogentcodebucketassignment.model.UserLoginResponse;
import com.example.cogentcodebucketassignment.utils.Constants;
import com.example.cogentcodebucketassignment.utils.Helper;
import com.example.cogentcodebucketassignment.utils.SessionManager;

public class LoginActivity extends AppCompatActivity implements FirestoreResponseListener, View.OnClickListener {
    //class-level variables
    private ActivityLoginBinding binding;
    FirestoreDbManager firestoreDbManager;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        firestoreDbManager = new FirestoreDbManager(this, this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //setting onClickListeners
        binding.forgotPassword.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_login) {
            if (validateFields()) {
                imm.hideSoftInputFromWindow(binding.userPassword.getWindowToken(), 0); // closing soft keyboard
                firestoreDbManager.loginUser(binding.userEmail.getText().toString(), binding.userPassword.getText().toString().trim());
            }
        } else if (id == R.id.btn_signup) {
            finish();
        } else if (id == R.id.forgot_password) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        }
    }

    //VALIDATION
    private boolean validateFields() {
        if (!Helper.EMAIL_PATTERN.matcher(binding.userEmail.getText().toString().trim()).matches()) {
            binding.userEmail.setError(getString(R.string.please_enter_valid_email));
            return false;
        }
        if (binding.userPassword.length() == 0) {
            binding.userPassword.setError("Please create your password");
            return false;

        }
        if (binding.userPassword.length() < 6) {
            binding.userPassword.setError("At least 6 characters long");
            return false;
        }

        return true;
    }


    @Override
    public void firestoreSuccessResponse(Object response, int serviceCode) {
        switch (serviceCode) {
            case Constants.CALL_LOGIN_USER:
                UserLoginResponse loginResponse = (UserLoginResponse) response;
                if (!loginResponse.doesUserExistsInDb())
                    Toast.makeText(this, " User doesn't exist.\nPlease Signup ", Toast.LENGTH_LONG).show();
                else if (!loginResponse.isPasswordCorrect()) {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
                } else {    // user successfully logged in
                    SessionManager.createUserSession((loginResponse.getUserDetails()));
                    startMainActivity();
                }
                break;
        }
    }

    @Override
    public void firestoreFailureResponse(String errorMessage) {
        Log.d(Constants.FIRESTORE, errorMessage);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }


    private void startMainActivity() {
        startActivity(new Intent(this, LanguagePreferenceActivity.class));
        finishAffinity();
    }
}