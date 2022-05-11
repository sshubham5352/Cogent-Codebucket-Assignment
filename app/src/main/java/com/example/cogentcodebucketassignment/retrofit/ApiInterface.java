package com.example.cogentcodebucketassignment.retrofit;

import com.example.cogentcodebucketassignment.model.responses.NewsTopHeadlineResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Call<NewsTopHeadlineResponse> fetchNewsTopHeadlines(@Query("apiKey") String ApiKey, @Query("country") String country);
}
