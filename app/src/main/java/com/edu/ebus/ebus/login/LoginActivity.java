package com.edu.ebus.ebus.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.MySingletonClass;
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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements FacebookCallback<com.facebook.login.LoginResult> {

    private CallbackManager callbackManager;
    private FirebaseFirestore mFirestore;
    private String TAG="eBus";
    private Button btnSignin;
    private TextView txtSigup;
    private EditText edUsername;
    private EditText edPassword;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnSignin =(Button) findViewById(R.id.btnSignIn);
        txtSigup = (TextView) findViewById(R.id.txt_signup);
        mFirestore=FirebaseFirestore.getInstance();
        
        // Check user login exists
        checkIfUserAlreadyLoggedIn();
        LoginButton btnFacebookLogin = findViewById(R.id.btn_facebook_login);
        btnFacebookLogin.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        btnFacebookLogin.registerCallback(callbackManager, this);

        // Sign in button retreive data from firestore
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mFirestore= FirebaseFirestore.getInstance();
                mFirestore.collection("userAccount")
                        .whereEqualTo("username",edUsername.getText().toString())
                        .whereEqualTo("password",edPassword.getText().toString())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getDocuments().size()==0){
                                Toast.makeText(LoginActivity.this,"Sign in Failedd",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                edPassword.setText("");
                                edUsername.setText("");
                                progressBar = new ProgressDialog(v.getContext());
                                progressBar.setCancelable(true);
                                progressBar.setMessage("Please Waiting ...");
                                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressBar.setProgress(0);
                                progressBar.setMax(100);
                                progressBar.show();
                                progressBar.dismiss();
                                progressBarStatus = 0;
                                fileSize = 0;
                                new Thread(new Runnable() {
                                    public void run() {
                                        while (progressBarStatus < 100) {
                                            progressBarStatus = downloadFile();
                                            try {
                                                Thread.sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            progressBarbHandler.post(new Runnable() {
                                                public void run() {
                                                    progressBar.setProgress(progressBarStatus);
                                                }
                                            });
                                        }
                                        if (progressBarStatus >= 100) {
                                            try {
                                                Thread.sleep(2000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            progressBar.dismiss();
                                        }
                                    }
                                }).start();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                //onBackPressed();
                                finish();
                                //Toast.makeText(LoginActivity.this,"Sign in Successed",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Log.d(TAG,"Error getting docmnet", task.getException());

                        }
                    }
                });
            }
        });
        txtSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNewAccountActivity.class );
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancel() {
        Profile profile = Profile.getCurrentProfile();
    }
    @Override

    public void onError(FacebookException error) {
        Toast.makeText(this, "Login with Facebook error.", Toast.LENGTH_LONG).show();
        Log.d("ckcc", "Facebook login error: " + error.getMessage());
    }

    public void ProgressDialog(){


    }

    public int downloadFile() {
        while (fileSize <= 1000000) {
            fileSize++;
            if (fileSize == 100000) {
                return 10;
            }else if (fileSize == 200000) {
                return 20;
            }else if (fileSize == 300000) {
                return 30;
            }else if (fileSize == 400000) {
                return 40;
            }else if (fileSize == 500000) {
                return 50;
            }else if (fileSize == 700000) {
                return 70;
            }else if (fileSize == 800000) {
                return 80;
            }
        }
        return 100;
    }
}
