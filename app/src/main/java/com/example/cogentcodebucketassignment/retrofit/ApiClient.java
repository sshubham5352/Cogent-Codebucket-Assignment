package com.example.cogentcodebucketassignment.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //static fields
    private static final String baseURL = "https://newsapi.org/v2/";
    public static Retrofit retrofit = null;

    private ApiClient() {
        //private empty constructor for singleton approach
    }

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
