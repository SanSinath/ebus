package com.edu.ebus.ebus.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Events;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

/**
 * Created by USER on 7/7/2018.
 */

public class EventActivitydetails extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_event_);

        Intent intent = getIntent();
        String eventJson = intent.getStringExtra("event");
        Gson gson = new Gson();
        Events event = gson.fromJson(eventJson, Events.class);

        SimpleDraweeView imageurl = findViewById (R.id.img_event);
        imageurl.setImageURI (event.getImageURL ());

        TextView texttitlle = findViewById (R.id.txt_tile_detail_vent);
        texttitlle.setText (event.getTitle ());

        TextView textdate = findViewById (R.id.txt_date_event);
        textdate.setText (event.getDate ());

        TextView texttimestart = findViewById (R.id.txt_time_start);
        texttimestart.setText (event.getTimestart ());

        TextView textlocation = findViewById (R.id.txt_location_event);
        textlocation.setText (event.getLocation ());

        TextView textphonenumber = findViewById (R.id.txt_phone_numbe_event);
        textphonenumber.setText (event.getPhonenumber ());

        TextView textprice = findViewById (R.id.txt_price_event);
        textprice.setText (event.getPrice ());






    }
}
