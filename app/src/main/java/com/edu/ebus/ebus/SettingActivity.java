package com.edu.ebus.ebus;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadLocale();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.setting));

        NavigationView settingNavigation = findViewById(R.id.setting_menu);
        settingNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        showUpdater();
                        break;
                    case R.id.language:
                        changeLanguage();
                        break;
                    case R.id.feedbacks:
                        break;
                    case R.id.helps:
                        break;
                    case R.id.abouts:
                        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    private void changeLanguage() {
        final String[] listItems = {"English(Defualt)","Khmer(ខ្មែរ)"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change language");
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    setLocale("en");
                    recreate();
                }
                if (which == 1){
                    setLocale("km");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // Save data to Shared Reference
        SharedPreferences.Editor editor = getSharedPreferences("ebus",MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }
    private void loadLocale(){
        SharedPreferences pref = getSharedPreferences("Setting",MODE_PRIVATE);
        String language = pref.getString("My_lang","");
        setLocale(language);
    }

    private void showUpdater() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.update_account, null))
                // Add action buttons
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Update data here

                        Toast.makeText(getApplicationContext(),"updated",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
