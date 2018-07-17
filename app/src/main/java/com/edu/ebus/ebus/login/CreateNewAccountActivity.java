package com.edu.ebus.ebus.login;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CreateNewAccountActivity extends AppCompatActivity{

    private Button btnSignUp;
    private Button bntCancel;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPhonenumber;
    private EditText mComfirmpassword;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        mFireStore = FirebaseFirestore.getInstance();
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        bntCancel = (Button) findViewById(R.id.btnCancel);
        mUsername = (EditText) findViewById(R.id.edUsername);
        mPhonenumber = (EditText) findViewById(R.id.edGmail_Phone);
        mPassword = (EditText) findViewById(R.id.edPassword);
        mComfirmpassword = (EditText) findViewById(R.id.edConfirmpassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username = mUsername.getText().toString();
                String Password = mPassword.getText().toString();
                String Phonenumber=mPhonenumber.getText().toString();
                String Confirmpassword=mComfirmpassword.getText().toString();
                if(Username.equals("") |Password.equals("") |Phonenumber.equals("") |Confirmpassword.equals("")){
                    Toast.makeText(CreateNewAccountActivity.this,"Please input your data to create account",Toast.LENGTH_LONG).show();
                }
                else if(Username.equals("")){
                    Toast.makeText(CreateNewAccountActivity.this,"Please input your name",Toast.LENGTH_LONG).show();
                }
                else if(Password.equals("")){
                    Toast.makeText(CreateNewAccountActivity.this,"Please input your name",Toast.LENGTH_LONG).show();
                }
                else if (Phonenumber.equals("")){
                    Toast.makeText(CreateNewAccountActivity.this,"Please input your Phone",Toast.LENGTH_LONG).show();
                }
                else if (Confirmpassword.equals("")){
                    Toast.makeText(CreateNewAccountActivity.this,"Please input your ",Toast.LENGTH_LONG).show();
                }

                else if(!Password.equals(Confirmpassword)){
                    Toast.makeText(CreateNewAccountActivity.this,"Your Password not match",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Map to Firestore
                    Map<String, String> userMap= new HashMap<>();
                    userMap.put("username",Username);
                    userMap.put("gmailorPhone",Phonenumber);
                    userMap.put("password",Password);
                    userMap.put("confirmPassword",Confirmpassword);
                    mFireStore.collection("userAccount").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(CreateNewAccountActivity.this, "Data to added to firestore",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(CreateNewAccountActivity.this, "error :"+ error,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewAccountActivity.this, com.edu.ebus.ebus.login.LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
