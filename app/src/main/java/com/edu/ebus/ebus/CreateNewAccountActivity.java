package com.edu.ebus.ebus;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CreateNewAccountActivity extends AppCompatActivity{

    private FirebaseAuth auth;
    private Button btnSignUp;
    private Button bntCancel;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mGmailorPhone;
    private EditText mComfirmpassword;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        auth = FirebaseAuth.getInstance();

        mFireStore = FirebaseFirestore.getInstance();
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        bntCancel = (Button) findViewById(R.id.btnCancel);
        mUsername = (EditText) findViewById(R.id.edUsername);
        mGmailorPhone = (EditText) findViewById(R.id.edGmail_Phone);
        mPassword = (EditText) findViewById(R.id.edPassword);
        mComfirmpassword = (EditText) findViewById(R.id.edConfirmpassword);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = mUsername.getText().toString();
                String Password = mPassword.getText().toString();
                String GmailPhone= mGmailorPhone.getText().toString();
                String Confirmpassword=mComfirmpassword.getText().toString();
//                if(Password.equals(Confirmpassword)){
//                    //Make Edittext NULL
//                    mUsername.setText("");
//                    mPassword.setText("");
//                    mGmailorPhone.setText("");
//                    mComfirmpassword.setText("");
//                    //Map to Firestore
//                    Map<String, String> userMap= new HashMap<>();
//                        userMap.put("username",Username);
//                        userMap.put("gmailorPhone",GmailoPhone);
//                        userMap.put("password",Password);
//                        userMap.put("confirmPassword",Confirmpassword);
//                        mFireStore.collection("userAccount").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(CreateNewAccountActivity.this, "Data to added to firestore",Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(Exception e) {
//                            String error = e.getMessage();
//                            Toast.makeText(CreateNewAccountActivity.this, "error :"+ error,Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(CreateNewAccountActivity.this,"Password not match",Toast.LENGTH_SHORT).show();
//                }

                auth.createUserWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(CreateNewAccountActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("ebus", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateNewAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewAccountActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
