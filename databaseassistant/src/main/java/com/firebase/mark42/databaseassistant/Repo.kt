package com.firebase.mark42.databaseassistant

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import java.util.HashMap

internal interface Repo<T> {
    suspend fun getFromDatabaseCache(path: String): T?
    fun getFromDatabase(path: String): LiveData<T?>
    suspend fun pushToDatabase(path: String, t: T): DatabaseResult<Unit>
    suspend fun postToDatabase(path: String, t: T): DatabaseResult<Unit>
    fun convertDatabaseSnapshot(snapShot: DataSnapshot): DatabaseResult<T?>
    suspend fun updateChildToDatabase(path: String, childPath: String, value: Any): DatabaseResult<Unit>
    suspend fun updateChildrenToDatabase(path: String, updates: HashMap<String, Any?>): DatabaseResult<Unit>
    suspend fun deleteFromDatabase(path: String): DatabaseResult<Unit>
    suspend fun getQueryFromDatabaseCache(query: Query): DatabaseResult<T?>
    fun getQueryFromDatabase(query: Query): LiveData<T?>
}