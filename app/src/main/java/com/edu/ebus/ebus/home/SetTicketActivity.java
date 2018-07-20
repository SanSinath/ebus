package com.edu.ebus.ebus.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.com.edu.ebus.ebus.activity.MainActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SetTicketActivity extends AppCompatActivity {
    private EditText number_phone;
    private EditText nunber_ticket;
    private EditText set_money;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private String codesent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.set_ticket);
        mAuth = FirebaseAuth.getInstance();
        Button tbpay = findViewById(R.id.bt_set_booking);
        number_phone = findViewById (R.id.txt_set_phonenumber);
        nunber_ticket = findViewById (R.id.txt_number_set_ticket);
        set_money = findViewById (R.id.txt_set_money);

        tbpay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                sentverificationcode();
            }
        });
    }

    private void sentverificationcode(){
        phoneNumber = number_phone.getText ().toString ();
        if (phoneNumber.isEmpty ()){
            number_phone.setError ("Phone number is required");
            number_phone.requestFocus ();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+855"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText (getApplication (),"PhoneAuthcteadentail",Toast.LENGTH_LONG).show ();
            Log.i("verify","verify success"+phoneAuthCredential);
            showDialogRquest();

        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText (getApplication (),"PhoneAuthcteaFaild",Toast.LENGTH_LONG).show ();
            Log.i("verify","verify fail"+e);
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent (s, forceResendingToken);
            codesent = s;
            Log.i("verify","code sent "+s+"     "+"verify Oncodesent"+forceResendingToken);
            Intent intent = new Intent (getApplication (),VerifyActivity.class);
            intent.putExtra ("number_phone",phoneNumber);
            intent.putExtra ("number_ticket",nunber_ticket.getText ().toString ());
            intent.putExtra ("set_money",set_money.getText ().toString ());
            intent.putExtra ("codesent",codesent);
            startActivity (intent);
            finish ();
        }
    };

    private void showDialogRquest() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SetTicketActivity.this);
        dialog.setTitle("Booking successed");
        dialog.setMessage("Thank you for booking , have a nice days.");
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), TicketDetialActivity.class);
                startActivity(i);
                finish();
            }
        });

        dialog.show();
    }

}

