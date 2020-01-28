package com.firebase.mark42.databaseassistant.java

import androidx.lifecycle.LiveData
import com.firebase.mark42.databaseassistant.DatabaseResult
import com.google.firebase.database.Query
import java.util.HashMap
import java.util.concurrent.CompletableFuture

internal interface Repo<T> {
    fun getFromDatabaseCache(path: String): CompletableFuture<T?>
    fun getFromDatabase(path: String): LiveData<T?>
    fun pushToDatabase(path: String, t: T): CompletableFuture<String?>
    fun updateChildToDatabase(path: String, value: Any): CompletableFuture<DatabaseResult<Unit>>
    fun updateChildrenToDatabase(path: String, updates: HashMap<String, Any?>):
            CompletableFuture<DatabaseResult<Unit>>
    fun deleteFromDatabase(path: String): CompletableFuture<DatabaseResult<Unit>>
    fun getQueryFromDatabaseCache(query: Query): CompletableFuture<T?>
    fun getQueryFromDatabase(query: Query): LiveData<T?>
}