package com.firebase.mark42.databasedemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.firebase.mark42.databaseassistant.DatabaseRepo
import com.firebase.mark42.databaseassistant.DatabaseResult
import com.google.firebase.database.DataSnapshot
import androidx.arch.core.util.Function
import com.firebase.mark42.databaseassistant.DatabaseSingleton

class UserRepo : DatabaseRepo<User>() {

    override fun convertDatabaseSnapshot(value: DataSnapshot?): User? {
        return value?.getValue(User::class.java)
    }

    companion object : DatabaseSingleton<UserRepo>(::UserRepo) {
        fun path() = "user/0"
    }
}