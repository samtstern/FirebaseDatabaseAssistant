package com.firebase.mark42.databasedemo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.firebase.mark42.databaseassistant.DatabaseRepo
import com.firebase.mark42.databaseassistant.DatabaseResult
import com.google.firebase.database.DataSnapshot
import androidx.arch.core.util.Function

class UserRepo : DatabaseRepo<User>() {

    override fun convertDatabaseSnapshot(value: DataSnapshot?): User? {
        return value?.getValue(User::class.java)
    }

    companion object {
        private var instance : UserRepo? = null

        fun getInstance(): UserRepo {
            if (instance == null)
                instance = UserRepo()

            return instance!!
        }
        fun userPath(path: String) = "users/$path"
        fun path() = "users"
    }
}