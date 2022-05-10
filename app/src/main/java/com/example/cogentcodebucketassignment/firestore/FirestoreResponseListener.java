package com.example.cogentcodebucketassignment.firestore;

import com.google.android.gms.tasks.OnSuccessListener;

public interface FirestoreResponseListener {
    void firestoreSuccessResponse(Object response, int serviceCode);
    void firestoreFailureResponse(String errorMessage);
}
