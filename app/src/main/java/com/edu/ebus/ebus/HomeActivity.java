package com.edu.ebus.ebus;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;


import com.edu.ebus.ebus.fragment.EventsFragment;
import com.edu.ebus.ebus.fragment.HomeFragment;
import com.edu.ebus.ebus.fragment.RecentlyFragment;
import com.edu.ebus.ebus.fragment.StationFragment;
import com.edu.ebus.ebus.fragment.UserFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Calendar;


public class HomeActivity extends AppCompatActivity {


    private UserAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        // Set toolbar
        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Defualt fragment
        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
        // Bottom Navigation handler click listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mhome:
                        getSupportActionBar().setTitle(getTitle());
                        HomeFragment homeFragment = new HomeFragment();
                        replaceFragment(homeFragment);
                        break;
                    case R.id.mevent:
                        getSupportActionBar().setTitle("Events");
                        EventsFragment eventsFragment = new EventsFragment();
                        replaceFragment(eventsFragment);
                        break;
                    case R.id.mrecently:
                        getSupportActionBar().setTitle("My Booking");
                        RecentlyFragment recentlyFragment = new RecentlyFragment();
                        replaceFragment(recentlyFragment);
                        break;
                    case R.id.mbusStation:
                        getSupportActionBar().setTitle("Station");
                        StationFragment stationFragment = new StationFragment();
                        replaceFragment(stationFragment);
                        break;
                    case R.id.muser:
                        getSupportActionBar().setTitle("Me");
                        UserFragment userFragment = new UserFragment();
                        replaceFragment(userFragment);
                        break;
                }
                return false;
            }
        });

    }

    private void replaceFragment(android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.framelayout, fragment);
        fragmentTransaction1.commit();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}
