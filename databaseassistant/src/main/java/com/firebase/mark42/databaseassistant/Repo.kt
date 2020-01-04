package com.firebase.mark42.databaseassistant

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import java.util.HashMap

internal interface Repo<T> {
    suspend fun getFromDatabaseCache(path: String): T?
    fun getFromDatabase(path: String): LiveData<T?>
    suspend fun pushToDatabase(path: String, t: T): String?
    suspend fun updateChildToDatabase(path: String, value: Any): Boolean
    suspend fun updateChildrenToDatabase(path: String, updates: HashMap<String, Any?>): Boolean
    suspend fun deleteFromDatabase(path: String): Boolean
    suspend fun getQueryFromDatabaseCache(query: Query): T?
    fun getQueryFromDatabase(query: Query): LiveData<T?>
}