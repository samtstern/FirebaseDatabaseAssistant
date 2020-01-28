package com.firebase.mark42.databasedemo

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.HashMap

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(context) as T
    }
}

class UserViewModel(context: Context) : ViewModel() {

    private val userRepo by lazy {
        UserRepo.getInstance(context)
    }

    fun getUserFromDatabase(activity: AppCompatActivity) {
        val fName = activity.findViewById<TextView>(R.id.firstName)
        val lName = activity.findViewById<TextView>(R.id.lastName)
        val email = activity.findViewById<TextView>(R.id.email)
        userRepo.getFromDatabase(UserRepo.userPath("-LxjJXsJdbZiEjjh1A89"))
            .observe(activity, Observer { user ->
            fName.text = user?.firstName
            lName.text = user?.lastName
            email.text = user?.email
        })
    }

    fun getUserFromCache(activity: AppCompatActivity) {
        viewModelScope.launch {
            val fName = activity.findViewById<TextView>(R.id.firstName)
            val lName = activity.findViewById<TextView>(R.id.lastName)
            val email = activity.findViewById<TextView>(R.id.email)
            val user = userRepo.getFromDatabaseCache(UserRepo.userPath("-LxjJXsJdbZiEjjh1A89"))
            fName.text = user?.firstName
            lName.text = user?.lastName
            email.text = user?.email
        }
    }

    fun pushUserToDatabase(user: User) {
        viewModelScope.launch {
            val value = userRepo.pushToDatabase(UserRepo.path(), user)
            user.userPushId = value
        }
    }

    fun updateUserFieldToDatabase() {
        viewModelScope.launch {
            val result = userRepo.updateChildToDatabase(UserRepo.userPath(
                "-LxjJoZry_g7uqwP3RRZ/" + DATABASE_KEY.USER.firstName), "Sachin")
            if (result.isSuccess()) {
                //Display success message
            } else {
                //Display error message
                //result.errorMessage
            }
        }
    }

    fun updateUserToDatabase(updates: HashMap<String, Any?>) {
        viewModelScope.launch {
            val result = userRepo.updateChildrenToDatabase(UserRepo.userPath(
                "-LxjLLWT2dtamozS2OIX"), updates)
            if (result.isSuccess()) {
                //Display success message
            } else {
                //Display error message
                //result.errorMessage
            }
        }
    }

    fun deleteFromDatabase() {
        viewModelScope.launch {
            val result = userRepo.deleteFromDatabase(UserRepo.userPath(
                "-LxjLLWT2dtamozS2OIX/" + DATABASE_KEY.USER.email))
            if (result.isSuccess()) {
                //Display success message
            } else {
                //Display error message
                //result.errorMessage
            }
        }
    }
}