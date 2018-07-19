package com.edu.ebus.ebus.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.com.edu.ebus.ebus.activity.MainActivity;
import com.edu.ebus.ebus.data.ChangeLanguage;
import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextUser;
    private EditText editTextPass;
    private EditText editTextPhone;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle(getResources().getString(R.string.setting));

        editTextUser = findViewById(R.id.ed_user);
        editTextPass = findViewById(R.id.ed_pass);
        editTextPhone = findViewById(R.id.ed_phone);

        LinearLayout linearLayoutAcc = findViewById(R.id.account_lyt);
        LinearLayout linearLayoutLang = findViewById(R.id.language_lyt);
        LinearLayout linearLayoutFeed = findViewById(R.id.feedbacks_lyt);
        LinearLayout linearLayoutHelp = findViewById(R.id.helps_lyt);
        LinearLayout linearLayoutAbout = findViewById(R.id.abouts_lyt);

        linearLayoutAcc.setOnClickListener(this);
        linearLayoutLang.setOnClickListener(this);
        linearLayoutFeed.setOnClickListener(this);
        linearLayoutHelp.setOnClickListener(this);
        linearLayoutAbout.setOnClickListener(this);


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
        builder.setTitle("Choose a language");
        builder.setSingleChoiceItems(listItems,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocale("en");
                    recreate();
                }else if (which == 1){
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
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (lang.equals("en")){
            Locale.setDefault(locale);
            config.locale = locale;
        }else if (lang.equals("km")){
            Locale.setDefault(locale);
            config.locale = locale;
        }
        resources.updateConfiguration(config, displayMetrics);
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
                }).setNegativeButton(R.string.notnow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void updateUser() {
        UserAccount account = MySingletonClass.getInstance().getAccount();
/*
        String eName;
        eName = editTextUser.getText().toString();
        String ePass;
        ePass = editTextPass.getText().toString();
        String ePhone;
        ePhone = editTextPhone.getText().toString();

        account.setUsername(eName);
        account.setPassword(ePass);
        account.setPhone(ePhone);

        MySingletonClass.getInstance().setAccount(account);
        Log.d("ebus","After update : " + account.getUsername() + account.getPassword() + account.getPhone());
        */
        final DocumentReference document = db.collection("userAccount").document(account.getId());
        document.get().addOnSuccessListener(new
                                                      OnSuccessListener<DocumentSnapshot>() {
                                                          public static final String WARMUPSSKIPPED = "userAccount";

                                                          @Override
                                                          public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                              UserAccount currentUser = documentSnapshot.toObject(UserAccount.class);
                                                              
                                                              Log.d("ebus", "DocumentSnapshot successfully retrieved! " + currentUser);
                                                              Map<String, Object> update = new HashMap<>();
                                                              update.put(WARMUPSSKIPPED, UserAccount.class);
                                                              document
                                                                      .set(update, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                  @Override
                                                                  public void onSuccess(Void aVoid) {
                                                                      Log.d("ebus", "Document has been saved");
                                                                  }
                                                              }).addOnFailureListener(new OnFailureListener() {
                                                                  @Override
                                                                  public void onFailure(@NonNull Exception e) {
                                                                      Log.d("ebus", "Document could not be saved");
                                                                  }
                                                              });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_lyt:
                showUpdater();
                break;
            case R.id.language_lyt:
                changeLanguage();
                loadLocale();
                break;
            case R.id.feedbacks_lyt:
                openGmailFeedBack();
                break;
            case R.id.helps_lyt:
                break;
            case R.id.abouts_lyt:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            default:
                    break;

        }
    }
}
