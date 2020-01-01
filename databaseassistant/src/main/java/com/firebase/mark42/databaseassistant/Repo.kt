package com.firebase.mark42.databaseassistant

internal interface Repo<T> {
    var cached: T?
    fun get(): T?

    suspend fun getFromDatabaseCache(path: String): T?
    suspend fun postToDatabase(path: String, t: T): Boolean
}