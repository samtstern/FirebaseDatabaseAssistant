package com.firebase.mark42.databasedemo

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    private val usersRepo by lazy {
        UsersRepo.getInstance()
    }

    fun getUsersFromDatabase(activity: AppCompatActivity) {
        usersRepo.getFromDatabase(UsersRepo.path()).observe(activity, Observer { users ->
            users?.forEach {
                // update your adapter
            }
        })
    }

    fun getUsersFromCache() {
        viewModelScope.launch {
            val users = usersRepo.getFromDatabaseCache(UsersRepo.path())
            users?.forEach {
                // update your adapter
            }
        }
    }

    fun queryUsersFromDatabase(activity: AppCompatActivity) {
        val query = FirebaseDatabase.getInstance().getReference("users")
            .orderByKey().limitToLast(2)
        usersRepo.getQueryFromDatabase(query).observe(activity, Observer { users ->
            users?.forEach {
                // update your adapter
            }
        })
    }

    fun queryUsersFromCache() {
        viewModelScope.launch {
            val query = FirebaseDatabase.getInstance().getReference("users")
                .orderByKey().limitToLast(2)
            val users = usersRepo.getQueryFromDatabaseCache(query)
            users?.forEach {
                // update your adapter
            }
        }
    }
}