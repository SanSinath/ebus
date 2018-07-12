package com.edu.ebus.ebus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by USER on 7/12/2018.
 */

public class SetTicketActivity extends AppCompatActivity {
    private Button tbpay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.set_ticket);
        tbpay = findViewById (R.id.bt_set_booking);

        tbpay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplication (),VerifyActivity.class);
                startActivity (intent);
            }
        });


    }


}
