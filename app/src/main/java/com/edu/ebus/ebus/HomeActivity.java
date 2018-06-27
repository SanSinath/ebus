package com.edu.ebus.ebus;

import android.animation.LayoutTransition;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mhome:
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.mevent:
                        EventsFragment eventsFragment = new EventsFragment();
                        FragmentManager fragmentManager1 = getFragmentManager();
                        android.app.FragmentTransaction transaction = fragmentManager1.beginTransaction();
                        transaction.replace(R.id.framelayout, eventsFragment);
                        transaction.commit();
                        break;
                    case R.id.mrecently:
                        RecentlyFragment recentlyFragment = new RecentlyFragment();
                        FragmentManager fragmentManager2 = getFragmentManager();
                        android.app.FragmentTransaction transaction1 = fragmentManager2.beginTransaction();
                        transaction1.replace(R.id.framelayout, recentlyFragment);
                        transaction1.commit();
                        break;
                    case R.id.mbusStation:

                        break;
                    case R.id.muser:
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
}
