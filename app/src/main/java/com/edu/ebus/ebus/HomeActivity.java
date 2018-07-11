package com.edu.ebus.ebus;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
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
import com.edu.ebus.ebus.fragment.UserFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Calendar;


public class HomeActivity extends AppCompatActivity {


    private UserAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_home);
        // Set toolbar
        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Defualt fragment
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, homeFragment);
        fragmentTransaction.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mhome:
                        getSupportActionBar().setTitle(getTitle());
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.mevent:
                        getSupportActionBar().setTitle("Events");
                        EventsFragment eventsFragment = new EventsFragment();
                        FragmentManager fragmentManager1 = getFragmentManager();
                        android.app.FragmentTransaction transaction = fragmentManager1.beginTransaction();
                        transaction.replace(R.id.framelayout, eventsFragment);
                        transaction.commit();
                        break;
                    case R.id.mrecently:
                        getSupportActionBar().setTitle("My Booking");
                        RecentlyFragment recentlyFragment = new RecentlyFragment();
                        FragmentManager fragmentManager2 = getFragmentManager();
                        android.app.FragmentTransaction transaction1 = fragmentManager2.beginTransaction();
                        transaction1.replace(R.id.framelayout, recentlyFragment);
                        transaction1.commit();
                        break;
                    case R.id.mbusStation:


                        break;
                    case R.id.muser:
                      //    getSupportActionBar().setTitle("Username");
                       getSupportActionBar().setTitle(account.getUsername());
                        UserFragment userFragment = new UserFragment();
                        FragmentManager fragmentManager3 = getFragmentManager();
                        android.app.FragmentTransaction fragmentTransaction1 = fragmentManager3.beginTransaction();
                        fragmentTransaction1.replace(R.id.framelayout, userFragment);
                        fragmentTransaction1.commit();
                        break;
                }
                return false;
            }
        });

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
