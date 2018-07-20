package com.edu.ebus.ebus.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.events.EventsFragment;
import com.edu.ebus.ebus.recent.RecentlyFragment;
import com.edu.ebus.ebus.station.StationFragment;
import com.edu.ebus.ebus.setting.UserFragment;


public class HomeActivity extends AppCompatActivity {

    private UserAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        // Set toolbar
        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);

        account = MySingletonClass.getInstance().getAccount();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.mhome:
                        getSupportActionBar().setTitle(getTitle());
                        HomeFragment homeFragment = new HomeFragment();
                        replaceFragment(homeFragment);
                        break;
                    case R.id.mevent:
                        getSupportActionBar().setTitle(getResources().getString(R.string.event));
                        EventsFragment eventsFragment = new EventsFragment();
                        replaceFragment(eventsFragment);
                        break;
                    case R.id.mrecently:
                        getSupportActionBar().setTitle(getResources().getString(R.string.recent_booking));
                        RecentlyFragment recentlyFragment = new RecentlyFragment();
                        replaceFragment(recentlyFragment);
                        break;
                    case R.id.mbusStation:
                        getSupportActionBar().setTitle(getResources().getString(R.string.station));
                        StationFragment stationFragment = new StationFragment();
                        replaceFragment(stationFragment);
                        break;
                    case R.id.muser:
                        if (account != null) {
                            getSupportActionBar().setTitle(account.getUsername());
                            Log.d("ebus","Toolbar username : "+account.getUsername());
                        }

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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
}
