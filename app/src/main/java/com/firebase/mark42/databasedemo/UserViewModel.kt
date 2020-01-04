package com.firebase.mark42.databasedemo

import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    fun loadUser(activity: AppCompatActivity) {
        val fName = activity.findViewById<TextView>(R.id.firstName)
        val lName = activity.findViewById<TextView>(R.id.lastName)
        val email = activity.findViewById<TextView>(R.id.email)
        val userRepo = UserRepo.getInstance()
        viewModelScope.launch {
            userRepo.getFromDatabase(UserRepo.path()).observe(activity, Observer { user ->
                Log.v("model", user.toString())
                fName.text = user?.firstName
                lName.text = user?.lastName
                email.text = user?.email
            })
        }
    }
}