package com.sachi.mark42.databasedemo

import com.sachi.mark42.databaseassistant.DatabaseRepo
import com.google.firebase.database.DataSnapshot

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