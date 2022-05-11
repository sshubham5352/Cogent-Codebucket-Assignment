package com.example.cogentcodebucketassignment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.adapters.NewsTopHeadlinesRvAdapter;
import com.example.cogentcodebucketassignment.databinding.FragmentHomeBinding;
import com.example.cogentcodebucketassignment.dialogs.ShareBottomSheetDialog;
import com.example.cogentcodebucketassignment.interfaces.NewsTopHeadlinesRvListener;
import com.example.cogentcodebucketassignment.model.responses.NewsTopHeadlineResponse;
import com.example.cogentcodebucketassignment.retrofit.ApiManager;
import com.example.cogentcodebucketassignment.retrofit.NetworkCallListener;
import com.example.cogentcodebucketassignment.utils.Constants;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, NetworkCallListener, NewsTopHeadlinesRvListener {
    //class level variables
    Context mContext;
    AppCompatActivity mActivity;
    FragmentHomeBinding binding;
    private ApiManager apiManager;
    ShareBottomSheetDialog bottomSheetDialog;
    private List<NewsTopHeadlineResponse.NewsArticle> newsArticles;


    //CONSTRUCTOR
    public HomeFragment(Context context, AppCompatActivity activity) {
        mContext = context;
        mActivity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
            apiManager = new ApiManager(mContext, this);
            fetchNewsTopHeadlines();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    //NETWORK CALL: WITH HEADER
    private void fetchNewsTopHeadlines() {
        apiManager.fetchNewsTopHeadlines("in");
    }

    @Override
    public void onNetworkCallSuccess(Object response, int serviceCode) {
        switch (serviceCode) {
            case Constants.NEWS_TOP_HEADLINES_CALL:
                NewsTopHeadlineResponse newsTopHeadlineResponse = (NewsTopHeadlineResponse) response;
                if (newsTopHeadlineResponse.getArticles().size() == 0) {
                    onNetworkCallFailure("something went wrong");
                    return;
                }

                newsArticles = newsTopHeadlineResponse.getArticles();
                binding.rvNewsTopHeadlines.setAdapter(new NewsTopHeadlinesRvAdapter(mContext, newsArticles, this));
                break;
        }
    }

    @Override
    public void onNetworkCallFailure(String errorMessage) {
        Log.d(Constants.NETWORK_CALL, errorMessage);
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        apiManager.detachListener();
        super.onDestroy();
    }

    @Override
    public void openShareBottomSheet() {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = new ShareBottomSheetDialog();
        }
        bottomSheetDialog.show(getActivity().getSupportFragmentManager(), Constants.BOTTOM_SHEET_SHARE);
    }
}
