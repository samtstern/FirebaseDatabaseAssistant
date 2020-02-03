package com.sach.mark42.databasedemo

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class App : Application() {

    companion object {
        var firebaseInstanceInitialized = false
    }

    override fun onCreate() {
        super.onCreate()

        if (!FirebaseApp.getApps(this).isEmpty() && !firebaseInstanceInitialized) {
            firebaseInstanceInitialized = true
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }
    }

}