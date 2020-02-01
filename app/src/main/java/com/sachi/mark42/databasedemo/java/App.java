package com.sachi.mark42.databasedemo.java;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    private static Boolean firebaseInstanceInitialized = false;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!FirebaseApp.getApps(this).isEmpty() && !firebaseInstanceInitialized) {
            firebaseInstanceInitialized = true;
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
