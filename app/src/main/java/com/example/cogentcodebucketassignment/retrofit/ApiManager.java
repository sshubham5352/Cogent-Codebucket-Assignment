package com.example.cogentcodebucketassignment.retrofit;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.cogentcodebucketassignment.model.responses.NewsTopHeadlineResponse;
import com.example.cogentcodebucketassignment.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    //class level variables
    public static final String APIKEY = "1a57427fc79a49bb9bd79c0ddcbc85d3";
    private ApiInterface apiInterface;
    private NetworkCallListener mListener;
    private final ProgressDialog progressDialog;

    //CONSTRUCTOR
    public ApiManager(Context context, NetworkCallListener networkCallListener) {
        this.mListener = networkCallListener;
        apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);

        //init progressBar
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }


    public void fetchNewsTopHeadlines(String country) {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<NewsTopHeadlineResponse> call = apiInterface.fetchNewsTopHeadlines(APIKEY, country);
        call.enqueue(new Callback<NewsTopHeadlineResponse>() {
            @Override
            public void onResponse(Call<NewsTopHeadlineResponse> call, Response<NewsTopHeadlineResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mListener.onNetworkCallSuccess(response.body(), Constants.NEWS_TOP_HEADLINES_CALL);
                } else
                    mListener.onNetworkCallFailure("onNetworkCallFailure " + response.code() + ": " + response.message());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NewsTopHeadlineResponse> call, Throwable t) {
                mListener.onNetworkCallFailure("onNetworkCallFailure " + ": " + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }


    /*
     * In order to prevent memory leaks
     * */
    public void detachListener() {
        mListener = null;
    }
}
