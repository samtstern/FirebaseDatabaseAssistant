package com.firebase.mark42.databasedemo

import com.firebase.mark42.databaseassistant.DatabaseRepo
import com.firebase.mark42.databaseassistant.DatabaseSingleton
import com.google.firebase.database.DataSnapshot

class UsersRepo : DatabaseRepo<List<User>>() {
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

    companion object : DatabaseSingleton<UsersRepo>(::UsersRepo) {
        fun path() = "users"
    }
}