package com.edu.ebus.ebus;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Method;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {
    private Button btnSignIn,btnFacebook;
    private EditText edUsername,edPassword;
    private TextView txtNewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnFacebook = findViewById(R.id.btnFacebook);
        txtNewAccount = findViewById(R.id.txtCreateNewAccount);
        txtNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateNewAccountActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginSuccess()){
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Check username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private boolean isLoginSuccess(){


        return true;
    }

    private boolean loginFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("username",edUsername.getText().toString().trim()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

            }
        });
        return true;
    }

    private void proccessLoginUser(Task<QuerySnapshot> task) {
        UserAccount[] accounts = new UserAccount[task.getResult().size()];
        int i=0;
        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
            UserAccount account = documentSnapshot.toObject(UserAccount.class);
            accounts[i] = account;
            i++;
        }
    }
}
