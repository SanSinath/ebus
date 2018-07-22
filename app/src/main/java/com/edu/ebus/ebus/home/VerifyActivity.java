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
import com.edu.ebus.ebus.com.edu.ebus.ebus.activity.MainActivity;
import com.edu.ebus.ebus.data.Booking;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.Ticket;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.recent.RecntlyActivity;
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

    private String codesent;
    private String nunber_ticket;
    private String set_money;
    private String phonedata;
    private EditText entercode;
    FirebaseAuth mAuth;
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
    private String id;
    private ProgressDialog progressBar;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_verify_layout);

        TextView dispay_number = findViewById(R.id.txt_numer_verify);
        entercode = findViewById(R.id.txt_enter_code_verify);
        TextView resentcode = findViewById(R.id.txt_redent_code);
        TextView wrongnumber = findViewById(R.id.txt_wrong_number);

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
        Log.d ("verify","n_t"+nunber_ticket+"   "+"s_m"+set_money+"  "+"p_d"+phonedata);

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
                Intent intent = new Intent (VerifyActivity.this, SetTicketActivity.class);
                startActivity (intent);
                finish ();
            }
        });

    }

    public  void onsubmit(View view){
        Log.d("verify", "onsubmit" + codesent);
        String code = entercode.getText().toString();
        if(code.isEmpty ()) {
            Toast.makeText (getApplication (),"Please Enter Code",Toast.LENGTH_LONG).show ();
        }
        else {
            loadingProgress();
            progressBar.show();
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
                            pushtofirebase();
                            showRequestDialog();
                            progressBar.dismiss();

                        } else {
                            progressBar.cancel();
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
            showRequestDialog();
            progressBar.cancel();
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
        FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
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
        userMap.put("docID",id);

        Booking booking = new Booking ();
        booking.setId (uID);
        booking.setDate(date);
        booking.setDestination (destination);
        booking.setSubtotal (subtotal);
        booking.setIdbus (idbus);
        booking.setMoney (set_money);
        booking.setScoce (scoce);
        booking.setNamecompany (namecompany);
        booking.setNumberticket (nunber_ticket);
        booking.setTime (time);
        booking.setDocID(id);
        Log.d("ebus","id before set : " + id);
        MySingletonClass.getInstance ().setBooking (booking);
        mFireStore.collection("userTicket").add(userMap).addOnSuccessListener (new OnSuccessListener<DocumentReference> () {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                id = documentReference.getId();
                Log.i ("verify","creat booking success");
            }
        }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(VerifyActivity.this, "error :"+ error,Toast.LENGTH_SHORT).show();
                Log.i ("verify","creat booking erorr" + error);
            }
        });
    }
    public void loadingProgress(){

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Proccess booking...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
    private void showRequestDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(VerifyActivity.this);
        dialog.setTitle(R.string.thank);
        dialog.setMessage(R.string.msgBooking);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplication(),TicketDetialActivity.class);
                startActivity(intent);
                finish ();
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplication(),HomeActivity.class);
                startActivity(intent);
                // exit the program
                finish();
            }
        });

        dialog.show();

    }
}
