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
import com.example.cogentcodebucketassignment.databinding.ActivitySignUpBinding;
import com.example.cogentcodebucketassignment.firestore.FirestoreDbManager;
import com.example.cogentcodebucketassignment.firestore.FirestoreResponseListener;
import com.example.cogentcodebucketassignment.model.UserDetails;
import com.example.cogentcodebucketassignment.model.UserSearchResponse;
import com.example.cogentcodebucketassignment.utils.Constants;
import com.example.cogentcodebucketassignment.utils.Helper;
import com.example.cogentcodebucketassignment.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, FirestoreResponseListener {
    //class-level variables
    private ActivitySignUpBinding binding;
    FirestoreDbManager firestoreDbManager;
    private InputMethodManager imm;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        firestoreDbManager = new FirestoreDbManager(this, this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //setting onClickListeners
        binding.forgotPassword.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.forgot_password) {
            startActivity(new Intent(this, ResetPasswordActivity.class));
        } else if (id == R.id.btn_signup) {
            if (validateFields()) {
                imm.hideSoftInputFromWindow(binding.confirmPassword.getWindowToken(), 0); // closing soft keyboard
                firestoreDbManager.searchUser(binding.userEmail.getText().toString().trim());
            }
        } else if (id == R.id.btn_login) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    //VALIDATION
    private boolean validateFields() {
        if (binding.userName.length() == 0) {
            binding.userName.setError(getString(R.string.please_enter_name));
            return false;
        }
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
        if (!binding.userPassword.getText().toString().matches(binding.confirmPassword.getText().toString())) {
            binding.confirmPassword.setError(getString(R.string.password_does_not_match));
            return false;
        }

        return true;
    }


    //FIRESTORE CALL
    private void signupUser() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put(Constants.FIELD_USER_NAME, binding.userName.getText().toString().trim());
        userDetails.put(Constants.FIELD_USER_EMAIL, binding.userEmail.getText().toString().trim());
        userDetails.put(Constants.FIELD_USER_PASSWORD, binding.userPassword.getText().toString().trim());

        firestoreDbManager.signupUser(userDetails);
    }

    @Override
    public void firestoreSuccessResponse(Object response, int serviceCode) {
        switch (serviceCode) {
            case Constants.CALL_SEARCH_USER:
                UserSearchResponse userSearchResponse = (UserSearchResponse) response;
                if (userSearchResponse.doesUserExistsInDb())
                    Toast.makeText(this, " User already exists.\nPlease Login ", Toast.LENGTH_LONG).show();
                else
                    signupUser();

                break;
            case Constants.CALL_SIGNUP_USER:
                UserDetails addedUserDetails = (UserDetails) response;
                SessionManager.createUserSession(addedUserDetails);
                moveToLanguagePrefActivity();
                break;
        }
    }

    @Override
    public void firestoreFailureResponse(String errorMessage) {
        Log.d(Constants.FIRESTORE, errorMessage);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void moveToLanguagePrefActivity() {
        startActivity(new Intent(this, LanguagePreferenceActivity.class));
        finishAffinity();
    }
}