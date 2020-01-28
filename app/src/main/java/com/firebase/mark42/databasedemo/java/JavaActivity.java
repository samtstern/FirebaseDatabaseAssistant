package com.firebase.mark42.databasedemo.java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.mark42.databasedemo.R;
import com.firebase.mark42.databasedemo.User;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        //userViewModel.getUserFromCache(this);
        userViewModel.getUserFromDatabase(this);
    }
}
