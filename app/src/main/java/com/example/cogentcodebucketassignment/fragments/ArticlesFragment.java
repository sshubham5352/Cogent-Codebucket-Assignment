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
import com.example.cogentcodebucketassignment.databinding.FragmentArticlesBinding;
import com.example.cogentcodebucketassignment.retrofit.ApiManager;
import com.example.cogentcodebucketassignment.retrofit.NetworkCallListener;
import com.example.cogentcodebucketassignment.utils.Constants;

public class ArticlesFragment extends Fragment implements NetworkCallListener, View.OnClickListener {
    //class level variables
    Context mContext;
    AppCompatActivity mActivity;
    FragmentArticlesBinding binding;
    private ApiManager apiManager;

    //CONSTRUCTOR
    public ArticlesFragment(Context context, AppCompatActivity activity) {
        mContext = context;
        mActivity = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false);
            apiManager = new ApiManager(mContext, this);
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


    @Override
    public void onNetworkCallSuccess(Object response, int serviceCode) {
        switch (serviceCode) {
            case Constants.NEWS_TOP_HEADLINES_CALL:
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
}