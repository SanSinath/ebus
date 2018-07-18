package com.edu.ebus.ebus.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.com.edu.ebus.ebus.activity.MainActivity;
import com.edu.ebus.ebus.data.ChangeLanguage;
import com.edu.ebus.ebus.home.HomeActivity;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity{

    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getResources().getString(R.string.setting));

        //loadLocale();

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
                        // Set local to activity
                        loadLocale();
                        break;
                    case R.id.feedbacks:
                        openGmailFeedBack();
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

    private void openGmailFeedBack() {
        Intent gmailFeedback = new Intent(Intent.ACTION_SEND);
        gmailFeedback.setType("text/email");
        gmailFeedback.putExtra(Intent.EXTRA_EMAIL, new String[]{"ebusteam.dev@gmail.com"});
        gmailFeedback.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        gmailFeedback.putExtra(Intent.EXTRA_TEXT,"Hi");
        startActivity(Intent.createChooser(gmailFeedback, "Sending feedback"));
    }

    private void changeLanguage() {

        final String[] listItems = {"English(Defualt)","Khmer(ខ្មែរ)"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a language").setCancelable(true);
        builder.setSingleChoiceItems(listItems,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocale("en");
                    recreate();
                }
                if(which == 1){
                    setLocale("km");
                    recreate();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Configuration config = new Configuration();
        if (lang.equals("en")){
            Locale.setDefault(locale);
            config.locale = locale;
        }else if (lang.equals("km")){
            Locale.setDefault(locale);
            config.locale = locale;
        }
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // Save data to Shared Reference
        SharedPreferences.Editor editor = getSharedPreferences("setting",MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    private void loadLocale(){
        SharedPreferences pref = getSharedPreferences("setting",MODE_PRIVATE);
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
                        updateUser();

                        Toast.makeText(getApplicationContext(),"updated",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void updateUser() {



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
