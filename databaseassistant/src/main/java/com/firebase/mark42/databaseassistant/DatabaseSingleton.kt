package com.firebase.mark42.databaseassistant

open class DatabaseSingleton<T>(private val data : () -> T) {
    private var instance: T? = null
    fun getInstance() : T {
        if (instance == null) {
            instance = data()
        }
        return instance!!
    }
}