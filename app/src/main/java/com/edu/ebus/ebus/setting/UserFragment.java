package com.edu.ebus.ebus.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.data.MySingletonClass;
import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.data.UserAccount;
import com.edu.ebus.ebus.login.LoginActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class UserFragment extends Fragment implements View.OnClickListener{

    private UserAccount account;
    private String userId;
    private SimpleDraweeView imgProfile;
    private TextView txt_name,textEmail,textPhone;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.user_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.usr_edit){
            startActivity(new Intent(getActivity(),SettingActivity.class));
        }
        if (item.getItemId() == R.id.usr_logout){

            if (account.getLoginMethod() == 1) {
                // Remove current user
                MySingletonClass.getInstance().setAccount(null);
                SharedPreferences preferences = getActivity().getSharedPreferences("ebus", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user");
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                Log.d("ebus", "Log out with firestore");
            }else if (account.getLoginMethod() == 2) {
                // Remove current user
                MySingletonClass.getInstance().setAccount(null);
                SharedPreferences preferences = getActivity().getSharedPreferences("ebus", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user");
                editor.apply();
                // Logout profile
                LoginManager.getInstance().logOut();
                AccessToken.setCurrentAccessToken(null);
                // Move to LoginActivity
                Intent fLogout = new Intent(getActivity(), LoginActivity.class);
                startActivity(fLogout);
                getActivity().finish();
                Log.d("ebus", "Log out with Facebook");
            }

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfile = view.findViewById(R.id.imgProfile);
        txt_name = view.findViewById(R.id.txtFullname);
        textEmail = view.findViewById(R.id.tvEmail);
        textPhone = view.findViewById(R.id.tvPhone);
        LinearLayout lytEmail = view.findViewById(R.id.lytEmail);


        imgProfile.setOnClickListener(UserFragment.this);

        account = MySingletonClass.getInstance().getAccount();

        Log.d("ebus","This is Login Method : " + account.getLoginMethod());

        if (account != null){
            userId = account.getId();
                imgProfile.setImageURI(account.getProfileImage());
                txt_name.setText(account.getUsername());
                textEmail.setText(account.getEmail());
                textPhone.setText(account.getPhone());

            Log.d("ebus" ,"name : " + account.getUsername() + "Profile images :" + account.getProfileImage()
                    + "Email : " + account.getEmail() + account.getPhone());
        }else {

            if (userId != null && account.getLoginMethod() == 2){
                loadProfileInfoFromFacebook();
            }
            else if (userId != null && account.getLoginMethod() == 1) {
                // load profile from firestore
                loadProfileInfoFromFirestore();
            }
        }
        loadProfileImageFromFirestore();

        NavigationView settingNavigation = view.findViewById(R.id.setting_menu);
        settingNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.language:
                        changeLanguage();
                        // Set local to activity
                        loadLocale();
                        break;
                    case R.id.feedbacks:
                        openGmailFeedBack();
                        break;
                    case R.id.abouts:
                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }
    private void loadProfileInfoFromFirestore() {
        firestore.collection("userAccount").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    UserAccount account = documentSnapshot.toObject(UserAccount.class);

                    assert account != null;
                    imgProfile.setImageURI(account.getProfileImage());
                    txt_name.setText(account.getUsername());
                    textEmail.setText(account.getEmail());
                    textPhone.setText(account.getPhone());

                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == 1){
            try {
                // Set image to image view
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                imgProfile.setImageBitmap(bitmap);

                // Save image to Firebase Storage
                uploadImageToFirebaseStorage(bitmap);

            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error while selecting profile image.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage(Bitmap bitmap){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference profileRef = storage.getReference().child("images").child("profiles").child(userId + ".jpg");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        profileRef.putBytes(bytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText( getActivity(), "Upload profile image success.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText( getActivity(), "Upload profile image fail.", Toast.LENGTH_LONG).show();
                    Log.d("ebus", "Upload profile image fail: " + task.getException());
                }
            }
        });
    }

    private void loadProfileImageFromFirestore(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("images").child("profiles").child(userId + ".jpg");
        profileRef.getBytes(10240000).addOnCompleteListener(new OnCompleteListener<byte[]>(){
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgProfile.setImageBitmap(bitmap);

                } else {
                    //Toast.makeText(getActivity(), "Load profile image fail.", Toast.LENGTH_LONG).show();
                    Log.d("ckcc", "Load profile image fail: " + task.getException());
                }
            }
        });
    }

    private void loadProfileInfoFromFacebook(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        final GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {

                            String id = object.getString("id");
                            String profileUrl = "http://graph.facebook.com/" + id + "/picture?type=large";
                            Log.d("ebus", "Profile picture" + profileUrl);
                            String name = object.getString("name");
                            String email = object.getString("email");

                            imgProfile.setImageURI(profileUrl);
                            txt_name.setText(name);
                            textEmail.setText(email);
                            textPhone.setText("empty");

                            Log.d("ebus","Facebook data : " + imgProfile + textPhone + textEmail + txt_name);
                        } catch (JSONException e) {
                            Log.d("ebus", "Load profile error" + e.getMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imgProfile){
            // Open gallery app to select an image
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a language").setCancelable(true);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    setLocale("en");
                    getActivity().recreate();
                }
                if (which == 1){
                    setLocale("km");
                    getActivity().recreate();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Configuration config = new Configuration();
        Resources resources = getActivity().getBaseContext().getResources();

        if (lang.equals("en")){
            Locale.setDefault(locale);
            config.locale = locale;
        }else if (lang.equals("km")){
            Locale.setDefault(locale);
            config.locale = locale;
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        // Save data to Shared Reference
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("setting", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }
    private void loadLocale(){
        SharedPreferences pref = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        String language = pref.getString("My_lang","");
        setLocale(language);
    }
    private void showUpdater() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.update_account, null))
                // Add action buttons
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Update data here
                        updateUser();
                        Toast.makeText(getActivity(),"updated",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void updateUser() {



    }

}
