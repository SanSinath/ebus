package com.edu.ebus.ebus.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements FacebookCallback<com.facebook.login.LoginResult> {

    private CallbackManager callbackManager;
    private FirebaseFirestore mFirestore;
    private String TAG="eBus";
    private EditText edUsername, edPassword;
    private TextInputLayout inputLayoutName, inputLayoutPass;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        Button btnSignin = findViewById(R.id.btnSignIn);
        TextView txtSigup = findViewById(R.id.txt_signup);

        inputLayoutName = findViewById(R.id.input_lyt_username);
        inputLayoutPass = findViewById(R.id.input_lyt_password);

        edUsername.addTextChangedListener(new MyTextWatcher(edUsername));
        edPassword.addTextChangedListener(new MyTextWatcher(edPassword));

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

                proccessLogin();

            }

        });
        txtSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNewAccountActivity.class );
                startActivity(intent);
            }
        });
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithFacebook();
            }
        });
    }

    private void requestFocus(EditText edUsername) {
        if (edUsername.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public class MyTextWatcher implements TextWatcher{
        private View view;
        private MyTextWatcher(View view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.edUsername:
                    validateName();
                    break;
                case R.id.edPassword:
                    validatePass();
                    break;
            }
        }
    }

    private boolean validateName() {
        String username = edUsername.getText().toString().trim();
        if (username.isEmpty()){
            inputLayoutName.setError("example: xxx.@gmail.com");
            requestFocus(edUsername);
            return false;
        }else {
            inputLayoutName.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePass(){
        String pass = edPassword.getText().toString().trim();
        if (pass.isEmpty()){
            inputLayoutPass.setError("type your password");
            requestFocus(edPassword);
            return false;
        }else {
            inputLayoutPass.setErrorEnabled(false);
        }
        return true;
    }
    private void proccessLogin(){
        if (!validateName()){
            return;
        }
        if (!validatePass()){
            return;
        }if (!validateName()&&!validatePass()){
            return;
        }
        else {
            loadingProgress();
            progressBar.show();
            mFirestore.collection("userAccount")
                    .whereEqualTo("email",edUsername.getText().toString())
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

                            // Save date from firestore
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            UserAccount account = documentSnapshot.toObject(UserAccount.class);
                            assert account != null;
                            account.setId(documentSnapshot.getId());

                            MySingletonClass.getInstance().setAccount(account);

                            // save profile in shared reference
                            saveProfileInSharedPref(account);
                            Log.d("ebus", "data " + account.getProfileImage() + account.getUsername());

                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        progressBar.dismiss();
                    }
                    else {
                        progressBar.cancel();
                        Log.d(TAG,"Error getting docmnet", task.getException());

                    }
                }
            });
        }
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
        if (userJsonString != null && MySingletonClass.getInstance().getLoginMethod() == 1) {
            Gson gson = new Gson();
            UserAccount user = gson.fromJson(userJsonString, UserAccount.class);
            MySingletonClass.getInstance().setAccount(user);
            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            // Finish current activity
            finish();

            Log.d("check","check user with firestore: ");
        }
        // check login via Facebook
        else if (AccessToken.getCurrentAccessToken() != null) {
            // Start MainActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            // Finish current activity
            finish();
            Log.d("check","check user with facebook: ");
        }
            Log.d("check","Both check user : ");

    }
    private void signUpWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_birthday"));
    }
    @Override
    public void onSuccess(com.facebook.login.LoginResult loginResult) {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {

                            String id = object.getString("id");
                            String profileUrl = "http://graph.facebook.com/" + id + "/picture?type=large";
                            Log.d("ebus", "Profile picture" + profileUrl);
                            String name = object.getString("name");

                            // Retrieved data from facebook
                            UserAccount account = new UserAccount();
                            account.setId(id);
                            account.setUsername(name);
                            account.setProfileImage(profileUrl);

                            Log.d("ebus", "data " + account.getProfileImage() + account.getUsername());
                            MySingletonClass.getInstance().setAccount(account);
                            saveProfileInSharedPref(account);

                        } catch (JSONException e) {
                            Log.d("ebus", "Load profile error" + e.getMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        request.setParameters(parameters);
        request.executeAsync();

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

    public void loadingProgress(){

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Sign in...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

}
