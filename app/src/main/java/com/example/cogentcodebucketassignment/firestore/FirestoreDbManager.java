package com.example.cogentcodebucketassignment.firestore;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cogentcodebucketassignment.model.UserDetails;
import com.example.cogentcodebucketassignment.model.UserLoginResponse;
import com.example.cogentcodebucketassignment.model.UserSearchResponse;
import com.example.cogentcodebucketassignment.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreDbManager {
    Context mContext;
    private final FirebaseFirestore db;
    private FirestoreResponseListener mListener;
    private ProgressDialog progressDialog;

    //SINGLETON CLASS
    public FirestoreDbManager(Context context, FirestoreResponseListener listener) {
        db = FirebaseFirestore.getInstance();
        mListener = listener;

        //init progressBar
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    public void searchUser(String userEmail) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Query query = db.collection(Constants.COLLECTION_USERS).whereEqualTo(Constants.FIELD_USER_EMAIL, userEmail);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 0) {
                        progressDialog.dismiss();
                        mListener.firestoreSuccessResponse(new UserSearchResponse(false), Constants.CALL_SEARCH_USER);
                    } else {
                        progressDialog.dismiss();
                        mListener.firestoreSuccessResponse(new UserSearchResponse(true), Constants.CALL_SEARCH_USER);
                    }
                }
            }
        });
    }

    public void loginUser(String userEmail, String password) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Query query = db.collection(Constants.COLLECTION_USERS).whereEqualTo(Constants.FIELD_USER_EMAIL, userEmail);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 0) {
                        mListener.firestoreSuccessResponse(new UserLoginResponse(false), Constants.CALL_LOGIN_USER);
                    } else {
                        String correctPassword = task.getResult().getDocuments().get(0).getString(Constants.FIELD_USER_PASSWORD);
                        if (password.matches(correctPassword)) {
                            UserDetails userDetails = task.getResult().getDocuments().get(0).toObject(UserDetails.class);
                            userDetails.setDocumentId(task.getResult().getDocuments().get(0).getId());
                            mListener.firestoreSuccessResponse(new UserLoginResponse(true, true, userDetails), Constants.CALL_LOGIN_USER);
                        } else
                            mListener.firestoreSuccessResponse(new UserLoginResponse(true, false), Constants.CALL_LOGIN_USER);
                    }
                }
                progressDialog.dismiss();
            }
        });
    }

    public void signupUser(Map<String, String> userDetails) {
        progressDialog.setMessage("Signing up...");
        progressDialog.show();
        String docId;

        DocumentReference docRef = db.collection(Constants.COLLECTION_USERS).document();
        docId = docRef.getId();

        docRef.set(userDetails)
                .addOnSuccessListener(aVoid -> {
                    UserDetails addedUser = new UserDetails(userDetails.get(Constants.FIELD_USER_NAME), userDetails.get(Constants.FIELD_USER_EMAIL), docId);
                    mListener.firestoreSuccessResponse(addedUser, Constants.CALL_SIGNUP_USER);
                    progressDialog.dismiss();

                })
                .addOnFailureListener(e -> {
                    mListener.firestoreFailureResponse(e.getMessage());
                    progressDialog.dismiss();
                });
    }
}
