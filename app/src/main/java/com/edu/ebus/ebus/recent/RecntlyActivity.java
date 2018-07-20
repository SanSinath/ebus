package com.edu.ebus.ebus.recent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.UserAccount;

import java.util.zip.Inflater;

/**
 * Created by USER on 7/2/2018.
 */

public class RecntlyActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_delails_booking);

        Toolbar toolbar = findViewById(R.id.rec_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UserAccount account = MySingletonClass.getInstance().getAccount();
        if (account!=null) {
            getSupportActionBar().setTitle(account.getUsername());
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
