package com.edu.ebus.ebus.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class CreateNewAccountActivity extends AppCompatActivity{

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPhonenumber;
    private EditText mComfirmpassword;
    private FirebaseFirestore mFireStore;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        mFireStore = FirebaseFirestore.getInstance();
        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button bntCancel = findViewById(R.id.btnCancel);
        mUsername = findViewById(R.id.edUsername);
        mEmail = findViewById(R.id.edEmail);
        mPhonenumber = findViewById(R.id.edGmail_Phone);
        mPassword = findViewById(R.id.edPassword);
        mComfirmpassword = findViewById(R.id.edConfirmpassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Username = mUsername.getText().toString();
                String Email = mEmail.getText().toString();
                String Password = mPassword.getText().toString();
                String Phonenumber=mPhonenumber.getText().toString();
                String Confirmpassword=mComfirmpassword.getText().toString();
                if(Username.isEmpty()){
                    mUsername.setError("require your name.");
                }
                if (Email.isEmpty()){
                    mEmail.setError("require your email.");

                }
                if(Password.isEmpty()){
                    mPassword.setError("require your password");
                }
                if (Phonenumber.isEmpty()){
                    mPhonenumber.setError("require your phone number");
                }
                if (Confirmpassword.isEmpty()){
                    mComfirmpassword.setError("require your re-type password");
                }
                if (Username.isEmpty()&& Email.isEmpty()&&Password.isEmpty()&&Phonenumber.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please fill the box.",Toast.LENGTH_SHORT).show();

                }else if(!Password.equals(Confirmpassword)){
                    Toast.makeText(CreateNewAccountActivity.this,"Your re-type password incorrect",Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingProgress();
                    //Map to Firestore
                    Map<String, String> userMap= new HashMap<>();
                    userMap.put("username",Username);
                    userMap.put("phone",Phonenumber);
                    userMap.put("email",Email);
                    userMap.put("password",Password);

                    mFireStore.collection("userAccount").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            loadNewUser();
                            progressBar.dismiss();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();

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
    public void loadingProgress(){
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Sign up...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

    }

    private void loadNewUser() {
        /*mFireStore.collection("userAccount").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    UserAccount account = documentSnapshot.toObject(UserAccount.class);
                    account.setId(documentSnapshot.getId());
                    // save profile reference
                    saveProfiletoSharedPreference(account);

                }else {
                    Toast.makeText(CreateNewAccountActivity.this, "load new user error ",Toast.LENGTH_SHORT).show();
                    Log.d("ebus","load new user error: " + task.getException());
                }
            }
        });*/
    }
    /*private void saveProfiletoSharedPreference(UserAccount account){
        SharedPreferences preferences = getSharedPreferences("ebus", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(account);
        editor.putString("user", userJson);
        editor.apply();

    }*/
}
