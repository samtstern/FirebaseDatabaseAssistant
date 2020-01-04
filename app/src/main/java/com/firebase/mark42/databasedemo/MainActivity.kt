package com.firebase.mark42.databasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.firebase.mark42.databaseassistant.DatabaseHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        //viewModel.getUserFromCache(this)
        viewModel.getUserFromDatabase(this)

    }
}
