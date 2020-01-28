package com.firebase.mark42.databasedemo

import android.content.Context
import com.firebase.mark42.databaseassistant.DatabaseRepo
import com.google.firebase.database.DataSnapshot

class UsersRepo(context: Context) : DatabaseRepo<List<User>>() {
    override fun convertDatabaseSnapshot(value: DataSnapshot?): List<User>? {
        val users = value?.children?.map {
            try {
                val user = it.getValue(User::class.java)
                user
            } catch (e: Exception) {
                null
            }
        }?.filterNotNull()

        return users
    }

    /*companion object : DatabaseSingleton<UsersRepo>(::UsersRepo) {
        fun path() = "users"
    }*/

    companion object {
        private var instance : UsersRepo? = null

        fun getInstance(context: Context): UsersRepo {
            if (instance == null)
                instance = UsersRepo(context)

            return instance!!
        }
        fun path() = "users"
    }
}