package com.edu.ebus.ebus.data;

import android.app.Activity;

public class ChangeLanguage {
    private Activity activity;

    private static ChangeLanguage instance;
    private ChangeLanguage(){

    }
    public static ChangeLanguage getInstance(){
        if (instance==null){
            instance = new ChangeLanguage();
        }
        return instance;
    }
    public Activity getActivity(){
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
