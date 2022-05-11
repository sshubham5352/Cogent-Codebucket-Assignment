package com.example.cogentcodebucketassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cogentcodebucketassignment.R;
import com.example.cogentcodebucketassignment.databinding.ActivityMainBinding;
import com.example.cogentcodebucketassignment.fragments.ArticlesFragment;
import com.example.cogentcodebucketassignment.fragments.HomeFragment;
import com.example.cogentcodebucketassignment.utils.Constants;
import com.example.cogentcodebucketassignment.utils.SessionManager;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    //class level variables
    ActivityMainBinding binding;
    FragmentManager fm;
    Map<String, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        onCreateFragmentManagement();

        binding.userName.setText(SessionManager.getUserName());
        View header = binding.drawerNavigation.getHeaderView(0);
        ((TextView) header.findViewById(R.id.user_name)).setText(SessionManager.getUserName());
        ((TextView) header.findViewById(R.id.user_email)).setText(SessionManager.getUserEmail());
        setDrawerNavigationListener();

        //setting Listeners
        binding.bottomNav.setOnItemSelectedListener(this);
        binding.hamburger.setOnClickListener(this);
        binding.bottomNav.setItemIconTintList(null);
    }

    private void setDrawerNavigationListener() {
        binding.drawerNavigation.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_sign_out) {
                SessionManager.logoutUser(this);
                startActivity(new Intent(this, SignUpActivity.class));
                finishAffinity();
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void onCreateFragmentManagement() {
        //setting Dashboard fragment
        fm = getSupportFragmentManager();
        fragmentMap.put(Constants.FRAGMENT_HOME, new HomeFragment(this, this));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragmentMap.get(Constants.FRAGMENT_HOME)).commit();
    }

    //on ViewClick
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.hamburger) {
            binding.drawerLayout.openDrawer((GravityCompat.START));
        }
    }


    //Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (binding.bottomNav.getSelectedItemId() == itemId)
            return false;
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragmentMap.get(Constants.FRAGMENT_HOME)).commit();
        } else {
            if (fragmentMap.get(Constants.FRAGMENT_ARTICLES) == null)
                fragmentMap.put(Constants.FRAGMENT_ARTICLES, new ArticlesFragment(this, this));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragmentMap.get(Constants.FRAGMENT_ARTICLES)).commit();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}