package com.akrama.learn2earn;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by akrama on 23/01/18.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}