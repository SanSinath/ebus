package com.edu.ebus.ebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignUp){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else{

        }
    }
}
