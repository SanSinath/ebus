package com.edu.ebus.ebus.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.Ticket;
import com.edu.ebus.ebus.data.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class VerifyActivity extends AppCompatActivity {

    private TextView dispay_number;
    private String codesent;
    private String nunber_ticket;
    private String set_money;
    private String phonedata;
    private EditText entercode;
    private TextView resentcode;
    private TextView wrongnumber;
    FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;
    // variable push to data firebase
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_verify_layout);

        dispay_number = findViewById(R.id.txt_numer_verify);
        entercode = findViewById(R.id.txt_enter_code_verify);
        resentcode = findViewById(R.id.txt_redent_code);
        wrongnumber = findViewById (R.id.txt_wrong_number);

        Intent intent = getIntent();
        phonedata = intent.getStringExtra("number_phone");
        codesent = intent.getStringExtra("codesent");
        nunber_ticket = intent.getStringExtra ("number_ticket");
        set_money = intent.getStringExtra ("set_money");

        dispay_number.setText("0"+phonedata);
        mAuth = FirebaseAuth.getInstance();

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

        // Log.i("verify","userID : "+ uID+ " Company : " + company + "Destination : " + des);
        Log.i ("verify","n_t"+nunber_ticket+"   "+"s_m"+set_money+"  "+"p_d"+phonedata);

        resentcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentverificationcode();
                Toast.makeText (getApplication (),"We've sent other code to your phone.",Toast.LENGTH_LONG).show ();
            }
        });
        wrongnumber.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplication (), SetTicketActivity.class);
                startActivity (intent);
                finish ();
            }
        });

    }

    public  void onsubmit(View view){
        Log.i("verify", "onsubmit" + codesent);
        String code = entercode.getText().toString();
        if(code.isEmpty ()) {
            Toast.makeText (getApplication (),"Please Enter Code",Toast.LENGTH_LONG).show ();
        }
        else {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential (codesent, code);
            signInWithPhoneAuthCredential (credential);
        }
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Verify succece", Toast.LENGTH_LONG).show();
                            pushtofirebase();
                            Intent intent = new Intent(getApplication(),TicketDetialActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText (getApplication (),"Code is wrong",Toast.LENGTH_LONG).show ();
                        }
                    }
                });
    }

    private void sentverificationcode(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+855"+phonedata,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.i("verify","verify success"+phoneAuthCredential);
            pushtofirebase();
            Intent intent = new Intent(getApplication(),TicketDetialActivity.class);
            startActivity(intent);
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
        userMap.put("numberticket",nunber_ticket);
        userMap.put("money",set_money);
        userMap.put("scoce",scoce);
        userMap.put("destination",destination);
        userMap.put ("date",date);
        userMap.put("time",time);

        Booking booking = new Booking ();
        booking.setDate (date);
        booking.setDestination (destination);
        booking.setSubtotal (subtotal);
        booking.setIdbus (idbus);
        booking.setMoney (set_money);
        booking.setScoce (scoce);
        booking.setPhonecompany(phonecompany);
        booking.setNamecompany (namecompany);
        booking.setNumberticket (nunber_ticket);
        booking.setTime (time);
        MySingletonClass.getInstance ().setBooking (booking);
        mFireStore.collection("userTicket").add(userMap).addOnSuccessListener (new OnSuccessListener<DocumentReference> () {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.i ("verify","creat booking success");
            }
        }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(VerifyActivity.this, "error :"+ error,Toast.LENGTH_SHORT).show();
                Log.i ("verify","creat booking erorr");
            }
        });
    }

}

