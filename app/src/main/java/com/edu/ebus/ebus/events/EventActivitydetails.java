package com.edu.ebus.ebus.events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Events;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


public class EventActivitydetails extends AppCompatActivity implements View.OnClickListener{

    private TextView textFulldetail;
    private Events event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_event_);

        Intent intent = getIntent();
        String eventJson = intent.getStringExtra("event");
        Gson gson = new Gson();
        event = gson.fromJson(eventJson, Events.class);

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

        LinearLayout lytLocation = findViewById(R.id.lyt_location);
        lytLocation.setOnClickListener(this);

        TextView textDetial = findViewById(R.id.txt_detail_all_event);
        textDetial.setText(event.getDetails());

        textphonenumber.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lyt_location:
                viewMaps();
                break;
            case R.id.txt_phone_numbe_event:
                makePhoneCall();
                break;

        }
    }

    private void makePhoneCall() {
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:023565656"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_DENIED){
            Toast.makeText(this,"please grant the permission to access phone call service",Toast.LENGTH_SHORT).show();
            requestPermission();
        }else {
            Toast.makeText(this,"Calling",Toast.LENGTH_SHORT).show();
            startActivity(call);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

    private void viewMaps() {

        Intent maps = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getLocationAddress()));
        startActivity(maps);

    }
}
