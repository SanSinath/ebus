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
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.home.HomeActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.zip.Inflater;

/**
 * Created by USER on 7/2/2018.
 */

public class RecntlyActivity  extends AppCompatActivity{
    float total=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.i("ebus","object booking");
        setContentView (R.layout.activity_delails_booking);

        Booking booking = MySingletonClass.getInstance ().getBooking ();
        TextView txtnumberticket = findViewById(R.id.txt_number_ticket);
        txtnumberticket.setText(booking.getNumberticket());
        int numberticket =Integer.parseInt(booking.getNumberticket ());

        TextView txtsubtotal = findViewById(R.id.txt_sumtotal_ticket);
        txtsubtotal.setText(booking.getSubtotal());
        float subtota =Float.parseFloat(booking.getSubtotal ());

        total = numberticket*subtota;
        TextView txttotalticket = findViewById(R.id.txt_total_ticket);
        txttotalticket.setText(total+"$");

        TextView txtmoney = findViewById(R.id.money_total);
        txtmoney.setText(total+"$");

        TextView txtnumbercompany = findViewById(R.id.txt_name_company);
        txtnumbercompany.setText(booking.getPhonecompany());

        TextView txtnamecompany = findViewById(R.id.txt_name_company_detail);
        txtnamecompany.setText(booking.getNamecompany());

        TextView txtsource = findViewById(R.id.txt_source_detail);
        txtsource.setText(booking.getScoce());

        TextView txtdetination = findViewById(R.id.detination_detail);
        txtdetination.setText(booking.getDestination());

        TextView txtdate = findViewById(R.id.txt_date_detail);
        txtdate.setText(booking.getDate());

        TextView txttime = findViewById(R.id.txt_time_detail);
        txttime.setText(booking.getTime());

        TextView txtbusid = findViewById(R.id.txt_idbus_detail);
        txtbusid.setText(booking.getIdbus());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ();
        Intent intent =  new  Intent(RecntlyActivity.this, HomeActivity.class);
        startActivity (intent);
        finish ();
    }
}
