package com.edu.ebus.ebus.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.home.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements FacebookCallback<com.facebook.login.LoginResult> {

    private CallbackManager callbackManager;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edUsername = findViewById(R.id.edUsername);
        final EditText edPassword = findViewById(R.id.edPassword);
        Button btnSignin = findViewById(R.id.btnSignIn);
        TextView txtCreateAccount = findViewById(R.id.txtCreateNewAccount);
        mFirestore=FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        // Check user login exists
        checkIfUserAlreadyLoggedIn();

        LoginButton btnFacebookLogin = findViewById(R.id.btn_facebook_login);
        btnFacebookLogin.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        btnFacebookLogin.registerCallback(callbackManager, this);

        // Sign in button retreive data from firestore
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore.collection("userAccount").document("ilt36gVlfClkbGPRLQo2").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                           UserAccount user = new UserAccount();
                           saveProfileInSharedPref(user);
                           Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                           startActivity(intent);
                           finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Sign in Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithFacebook();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void saveProfileInSharedPref(UserAccount user) {
        SharedPreferences preferences = getSharedPreferences("ebus", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userJsonString = gson.toJson(user);
        editor.putString("user", userJsonString);
        editor.apply();
    }

    private void checkIfUserAlreadyLoggedIn() {
        // Check login via username/password
        SharedPreferences preferences = getSharedPreferences("ebus", MODE_PRIVATE);
        String userJsonString = preferences.getString("user", null);
        if (userJsonString != null) {

            Gson gson = new Gson();
            UserAccount user = gson.fromJson(userJsonString, UserAccount.class);
            MySingletonClass.getInstance().setAccount(user);

            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            // Finish current activity
            finish();
        }
        // check login via Facebook
        if (AccessToken.getCurrentAccessToken() != null) {

            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            // Finish current activity
            finish();
        }
    }
    private void signUpWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_birthday"));
    }
    @Override
    public void onSuccess(com.facebook.login.LoginResult loginResult) {
        Profile profile = Profile.getCurrentProfile();
//        Gson gson = new Gson();
//
//        UserAccount account = MySingletonClass.getInstance().setAccount(profile);
//
//
//        saveProfileInSharedPref(account);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onCancel() {
        Toast.makeText(getApplicationContext(), "Login canceled.",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, "Login with Facebook error.", Toast.LENGTH_LONG).show();
        Log.d("ckcc", "Facebook login error: " + error.getMessage());
    }
}
