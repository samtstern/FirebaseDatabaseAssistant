package com.sachi.mark42.databasedemo

import com.sachi.mark42.databaseassistant.DatabaseRepo
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

    companion object {
        private var instance : UsersRepo? = null

        fun getInstance(): UsersRepo {
            if (instance == null)
                instance = UsersRepo()

            return instance!!
        }
        fun path() = "users"
    }
}