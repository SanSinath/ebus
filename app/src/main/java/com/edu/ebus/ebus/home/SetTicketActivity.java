package com.edu.ebus.ebus.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.Events;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.Ticket;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.home.VerifyActivity;
import com.edu.ebus.ebus.recent.RecntlyActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SetTicketActivity extends AppCompatActivity {
    private Button tbpay;
    private EditText number_phone;
    private EditText nunber_ticket;
    private EditText set_money;
    private String phoneNumber;
    private FirebaseAuth mAuth;
    private String codesent;

    // variable push to data firebase
    private FirebaseFirestore mFireStore;
    private String uID;
    private String namecompany;
    private String phonecompany;
    private String idbus;
    private String subtotal;
    private String scoce;
    private String destination;
    private String date;
    private String time;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.set_ticket);
        mAuth = FirebaseAuth.getInstance();
        tbpay = findViewById (R.id.bt_set_booking);
        number_phone = findViewById (R.id.txt_set_phonenumber);
        nunber_ticket = findViewById (R.id.txt_number_set_ticket);
        set_money = findViewById (R.id.txt_set_money);


        //get data from MysingletonClass
        Ticket ticket = MySingletonClass.getInstance ().getTicket ();
        UserAccount account = MySingletonClass.getInstance ().getAccount ();
        uID = account.getId ();
        username = account.getUsername ();
        namecompany = ticket.getName ();
        phonecompany = ticket.getPhonenumber ();
        idbus = ticket.getIdbus ();
        subtotal = ticket.getPrice ();
        scoce = ticket.getSource ();
        destination = ticket.getDestination ();
        date = ticket.getDateofBooking ();
        time = ticket.getHour ();

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
            //put data to firebase
            pushtofirebase();
            Intent intent = new Intent (SetTicketActivity.this, RecntlyActivity.class);
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
            Intent intent = new Intent (getApplication (),VerifyActivity.class);
            intent.putExtra ("number_phone",phoneNumber);
            intent.putExtra ("number_ticket",nunber_ticket.getText ().toString ());
            intent.putExtra ("set_money",set_money.getText ().toString ());
            intent.putExtra ("codesent",codesent);
            startActivity (intent);
            finish ();
            Log.i("verify","code sent "+s+"     "+"verify Oncodesent"+forceResendingToken);
        }
    };

    public void pushtofirebase(){
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();
        ///  get from sharing reference from useraccount da

        Map<String, String> userMap= new HashMap<> ();
        userMap.put ("uID",uID);
        userMap.put ("username",username);
        userMap.put("namecompany",namecompany);
        userMap.put("phonecompany",phonecompany);
        userMap.put("idbus",idbus);
        userMap.put("subtotal",subtotal);
        userMap.put("numberticket",nunber_ticket.getText ().toString ());
        userMap.put("money",set_money.getText ().toString ());
        userMap.put("scoce",scoce);
        userMap.put("destination",destination);
        userMap.put ("date",date);
        userMap.put("time",time);

        Booking booking = new Booking ();
        booking.setDate (uID);
        booking.setDestination (destination);
        booking.setSubtotal (subtotal);
        booking.setIdbus (idbus);
        booking.setMoney (set_money.getText ().toString ());
        booking.setScoce (scoce);
        booking.setNamecompany (namecompany);
        booking.setNumberticket (nunber_ticket.getText ().toString ());
        booking.setTime (time);
        MySingletonClass.getInstance ().setBooking (booking);
        Log.i ("verify","creat booking success"+booking.getIdbus ());

        mFireStore.collection("userTicket").add(userMap).addOnSuccessListener (new OnSuccessListener<DocumentReference> () {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i ("verify","creat booking success");
            }
        }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(SetTicketActivity.this, "error :"+ error,Toast.LENGTH_SHORT).show();
                Log.i ("verify","creat booking erorr");
            }
        });
    }

}

