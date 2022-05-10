package com.example.cogentcodebucketassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.adapters.LanguagePreferenceRvAdapter;
import com.example.cogentcodebucketassignment.databinding.ActivityLanguagePreferenceBinding;
import com.example.cogentcodebucketassignment.model.LanguageItem;
import com.example.cogentcodebucketassignment.utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguagePreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    //CLASS-LEVEL VARIABLES
    private ActivityLanguagePreferenceBinding binding;
    private List<LanguageItem> languagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_preference);

        //init rvLanguages
        populateLanguageList();
        binding.rvLanguages.setAdapter(new LanguagePreferenceRvAdapter(this, languagesList));

        //setting onClickListeners
        binding.btnContinue.setOnClickListener(this);
    }

    private void populateLanguageList() {
        List<String> languageNames = Arrays.asList(getResources().getStringArray(R.array.languageNames));
        List<String> languageLocalNames = Arrays.asList(getResources().getStringArray(R.array.languageLocalNames));
        languagesList = new ArrayList<>();

        for (int i = 0; i < languageNames.size(); i++)
            languagesList.add(new LanguageItem(languageNames.get(i), languageLocalNames.get(i)));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_continue) {
            int position = ((LanguagePreferenceRvAdapter) binding.rvLanguages.getAdapter()).getSelectedLanguagePosition();
            SessionManager.saveLanguagePreference(languagesList.get(position).getLanguageName(), position);
            startMainActivity();
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}