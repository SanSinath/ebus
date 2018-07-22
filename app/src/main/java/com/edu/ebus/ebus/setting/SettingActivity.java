package com.edu.ebus.ebus.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.UserAccount;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity{

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPhonenumber;
    private FirebaseFirestore mFirestore;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        mUsername = findViewById(R.id.setting_username);
        mEmail = findViewById(R.id.setting_Email);
        mPassword = findViewById(R.id.setting_password);
        mPhonenumber =findViewById(R.id.setting_Phonenumber);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.account));
        final UserAccount account= MySingletonClass.getInstance().getAccount();

        //Button Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = mUsername.getText().toString().trim();
                String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                String Phonenumber=mPhonenumber.getText().toString().trim();
                if (Username.isEmpty()|Email.isEmpty()|Password.isEmpty()|Phonenumber.isEmpty()){
                    Toast.makeText(SettingActivity.this,"Please input data to upadate your account",Toast.LENGTH_LONG).show();
                }
                else {
                    loadingProgress();
                    progressBar.show();
                    CollectionReference noteRef = mFirestore.collection("userAccount");
                    Map<String ,String> userMap= new HashMap<>();
                    userMap.put("username",Username);
                    userMap.put("phone",Phonenumber);
                    userMap.put("email",Email);
                    userMap.put("password",Password);
//                    mFirestore.collection("userAccount")
                    noteRef.document(account.getId()).set(userMap);
                    Intent intent = new Intent(SettingActivity.this,UserFragment.class);
                    startActivity(intent);
                    progressBar.cancel();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void loadingProgress(){

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Updating ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

}
