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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

/**
 * Created by USER on 7/12/2018.
 */

public class VerifyActivity extends AppCompatActivity {

    private TextView dispay_number;
    private Button bt_sumit;
    private String codesent;
    private EditText entercode;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.code_verify_layout);

        dispay_number= findViewById (R.id.txt_numer_verify);
        bt_sumit = findViewById (R.id.btn_Submit);
        entercode = findViewById (R.id.txt_enter_code_verify);


        Intent intent = getIntent ();
        String data = intent.getStringExtra ("number_phone");
        codesent = intent.getStringExtra ("codesent");



        Toast.makeText (getApplication (),"Intent data phone"+data,Toast.LENGTH_LONG).show ();
        Toast.makeText (getApplication (),"Intent data code sent"+codesent,Toast.LENGTH_LONG).show ();
        // Toast.makeText (getApplication (),"Intent data code enter"+code,Toast.LENGTH_LONG).show ();
        dispay_number.setText (data);

        bt_sumit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String code = entercode.getText ().toString ();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesent,code);
                signInWithPhoneAuthCredential(credential);
            }

        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText (getApplication (),"Verify succece",Toast.LENGTH_LONG).show ();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });


    }

}
