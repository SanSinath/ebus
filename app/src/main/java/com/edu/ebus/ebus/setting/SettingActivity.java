package com.edu.ebus.ebus.setting;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.edu.ebus.ebus.home.HomeActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity{

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPhonenumber;
    private EditText mPassword;
    private FirebaseFirestore mFirestore;
    private ProgressDialog progressBar;
    private UserAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        mUsername = findViewById(R.id.setting_username);
        mEmail = findViewById(R.id.setting_Email);
        mPassword = findViewById(R.id.setting_pass);
        mPhonenumber =findViewById(R.id.setting_Phonenumber);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        account = MySingletonClass.getInstance().getAccount();
        getSupportActionBar().setTitle(account.getUsername());
        account= MySingletonClass.getInstance().getAccount();

        //Button Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = mUsername.getText().toString().trim();
                String Email = mEmail.getText().toString().trim();
                String Phonenumber=mPhonenumber.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                if (Username.isEmpty()|Email.isEmpty()|Phonenumber.isEmpty()){
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
                    userMap.put("password",pass);
//                    mFirestore.collection("userAccount")
                    noteRef.document(account.getId()).set(userMap);
                    account.setEmail(Email);
                    account.setUsername(Username);
                    account.setPhone(Phonenumber);
                    account.setPassword(pass);
                    MySingletonClass.getInstance().setAccount(account);
                    saveProfileInSharedPref(account);
                    startActivity(new Intent(SettingActivity.this,HomeActivity.class));
                    finish();

                }
                progressBar.cancel();
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

    private void saveProfileInSharedPref(UserAccount user) {
        SharedPreferences preferences = getSharedPreferences("ebus", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        editor.putString("user", userJsonString);
        editor.apply();
    }
}
