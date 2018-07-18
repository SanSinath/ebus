package com.edu.ebus.ebus.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.home.VerifyActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SetTicketActivity extends AppCompatActivity {
    private Button tbpay;
    private EditText number_phone;
    String phoneNumber;
    FirebaseAuth mAuth;
    String codesent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.set_ticket);
        mAuth = FirebaseAuth.getInstance();
        tbpay = findViewById (R.id.bt_set_booking);
        number_phone = findViewById (R.id.txt_set_phonenumber);



        tbpay.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                sentverificationcode();
                Toast.makeText (getApplication (),"phonnumber"+phoneNumber+"codesent"+codesent,Toast.LENGTH_LONG).show ();
            }
        });
    }

    private void sentverificationcode(){

        phoneNumber = number_phone.getText ().toString ();
        Toast.makeText (getApplication (),"phoneumer"+phoneNumber,Toast.LENGTH_LONG).show ();
        if (phoneNumber.isEmpty ()){
            number_phone.setError ("Phone number is required");
            number_phone.requestFocus ();
            return;
        }
//        if (phoneNumber.length ()<10){
//            number_phone.setError ("Please enter a valid phone");
//            number_phone.requestFocus ();
//            return;
//        }

        Toast.makeText (getApplication (),"callProvider",Toast.LENGTH_LONG).show ();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+855"+phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText (getApplication (),"PhoneAuthcteadentail",Toast.LENGTH_LONG).show ();
            Log.i("verify","verify success"+phoneAuthCredential);

            Intent intent = new Intent (getApplication (),VerifyActivity.class);
            intent.putExtra ("number_phone",phoneNumber);
            intent.putExtra ("codesent",codesent);
            startActivity (intent);
            finish ();


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
        }




    };

}

