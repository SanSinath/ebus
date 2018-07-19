package com.edu.ebus.ebus.recent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.Events;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.zip.Inflater;

/**
 * Created by USER on 7/2/2018.
 */

public class RecntlyActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.i("ebus","object booking");
        setContentView (R.layout.activity_delails_booking);

    }
}
