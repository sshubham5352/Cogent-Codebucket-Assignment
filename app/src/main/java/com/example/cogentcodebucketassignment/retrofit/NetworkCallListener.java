package com.example.cogentcodebucketassignment.retrofit;

public interface NetworkCallListener {

    void onNetworkCallSuccess(Object response, int serviceCode);
    void onNetworkCallFailure(String errorMessage);
}
