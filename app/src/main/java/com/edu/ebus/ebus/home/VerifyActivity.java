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
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.recent.RecntlyActivity;
import com.edu.ebus.ebus.station.StationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by USER on 7/12/2018.
 */

public class VerifyActivity extends AppCompatActivity {

    private TextView dispay_number;
    private Button bt_sumit;
    private String codesent;
    private String phonedata;
    private EditText entercode;
    private TextView resentcode;
    private TextView wrongnumber;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_verify_layout);

        dispay_number = findViewById(R.id.txt_numer_verify);
        bt_sumit = findViewById(R.id.btn_Submit);
        entercode = findViewById(R.id.txt_enter_code_verify);
        resentcode = findViewById(R.id.txt_redent_code);
        wrongnumber = findViewById (R.id.txt_wrong_number);


        final Intent intent = getIntent();
        phonedata = intent.getStringExtra("number_phone");
        codesent = intent.getStringExtra("codesent");

        Toast.makeText(getApplication(), "Intent data phone" + phonedata, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), "Intent data code sent" + codesent, Toast.LENGTH_LONG).show();
        // Toast.makeText (getApplication (),"Intent data code enter"+code,Toast.LENGTH_LONG).show ();
        dispay_number.setText(phonedata);
        mAuth = FirebaseAuth.getInstance();

        resentcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentverificationcode();
            }
        });
        wrongnumber.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplication (),SetTicketActivity.class);
                startActivity (intent);
                finish ();
            }
        });

    }

    public  void onsubmit(View view){
        Log.i("verify", "onsubmit" + codesent);
        String code = entercode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Verify succece", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(),RecntlyActivity.class);
                            startActivity(intent);

                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });


    }

    private void sentverificationcode(){


        Toast.makeText (getApplication (),"phoneumer"+phonedata,Toast.LENGTH_LONG).show ();

        Toast.makeText (getApplication (),"callProvider",Toast.LENGTH_LONG).show ();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+855"+phonedata,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks () {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Toast.makeText(getApplication(), "PhoneAuthcteadentail", Toast.LENGTH_LONG).show();
            Log.i("verify", "verify success" + phoneAuthCredential);
            Toast.makeText(getApplication(), "phonnumber" + phonedata.toString() + "codesent" + codesent, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codesent = s;
            Log.i("verify", "code sent " + s + "     " + "verify Oncodesent" + forceResendingToken);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplication(), "PhoneAuthcteaFaild", Toast.LENGTH_LONG).show();
            Log.i("verify", "verify fail" + e);

        }

    };

}
