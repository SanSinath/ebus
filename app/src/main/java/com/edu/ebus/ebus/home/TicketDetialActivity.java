package com.edu.ebus.ebus.home;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.MySingletonClass;

public class TicketDetialActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delails_booking);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.ticket);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Booking booking = MySingletonClass.getInstance ().getBooking ();
        getSupportActionBar().setTitle(booking.getUsername());
        TextView txtnumberticket = findViewById(R.id.txt_number_ticket);
        txtnumberticket.setText(booking.getNumberticket());
        int numberticket =Integer.parseInt(booking.getNumberticket ());

        TextView txtsubtotal = findViewById(R.id.txt_sumtotal_ticket);
        txtsubtotal.setText(booking.getMoney());
        float subtota =Float.parseFloat(booking.getMoney());

        float total = numberticket * subtota;
        TextView txttotalticket = findViewById(R.id.txt_total_ticket);
        txttotalticket.setText(total +"$");

        TextView txtmoney = findViewById(R.id.money_total);
        txtmoney.setText(total +"$");

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
