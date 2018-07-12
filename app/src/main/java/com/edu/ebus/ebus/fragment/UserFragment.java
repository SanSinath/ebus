package com.edu.ebus.ebus.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.ebus.ebus.LoginActivity;
import com.edu.ebus.ebus.MySingletonClass;
import com.edu.ebus.ebus.R;
import com.edu.ebus.ebus.SettingActivity;
import com.edu.ebus.ebus.UserAccount;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class UserFragment extends android.app.Fragment implements View.OnClickListener{

    private final String userId = "abc123";

    private SimpleDraweeView imgProfile;
    private TextView txt_name;
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
        switch (item.getItemId()) {
            case R.id.usr_setting :
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.usr_feedback:
                // Remove current user
                MySingletonClass.getInstance().setAccount(null);
                SharedPreferences preferences = getActivity().getSharedPreferences("ebus", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user");
                editor.apply();

                LoginManager.getInstance().logOut();
                // Move to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(),"You logouted complete",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgProfile = view.findViewById(R.id.imgProfile);
        txt_name = view.findViewById(R.id.txtFullname);

        // Load profile from firestore
        loadProfileImageFromFirestore();

        //loadProfileImageFromFirestore();
        loadProfileInfoFromFacebook();

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
        /*FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference profileRef = storage.getReference().child("images").child("profiles").child(userId + ".jpg");
        profileRef.getBytes(10240000).addOnCompleteListener(new OnCompleteListener<byte[]>(){
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                if(task.isSuccessful()){
                    byte[] bytes = task.getResult();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgProfile.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getActivity(), "Load profile image fail.", Toast.LENGTH_LONG).show();
                    Log.d("ckcc", "Load profile image fail: " + task.getException());
                }
            }
        });*/
    }

    private void loadProfileInfoFromFacebook(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String profileUrl = "http://graph.facebook.com/" + id + "/picture?type=large";
                            Log.d("ebus", "Profile picture" + profileUrl);
                            String name = object.getString("name");

                            imgProfile.setImageURI(profileUrl);
                            txt_name.setText(name);


                        } catch (JSONException e) {
                            Log.d("ebus", "Load profile error" + e.getMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
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
}
