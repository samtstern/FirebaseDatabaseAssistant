package com.sachi.mark42.databaseassistant.java

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sachi.mark42.databaseassistant.DatabaseApi
import com.sachi.mark42.databaseassistant.DatabaseResult
import com.google.firebase.database.DataSnapshot

import androidx.arch.core.util.Function
import com.google.firebase.database.Query
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.HashMap
import java.util.concurrent.CompletableFuture

abstract class DatabaseRepo<T>(): Repo<T> {
    private val api = DatabaseApi<T>()

    override fun getFromDatabase(path: String) : LiveData<T?> {
        val result = api.getFromDatabase(path)
        val function = Function<DatabaseResult<DataSnapshot>, T> {
            if (it.isSuccess()) {
                convertDatabaseSnapshot(it.value)
            } else {
                null
            }
        }
        return Transformations.map(result, function)
    }

    override fun getFromDatabaseCache(path: String) : CompletableFuture<T?> =
        GlobalScope.future {
            val result = api.getFromDatabaseCache(path)
            convertDatabaseSnapshot(result.value)
        }

    override fun pushToDatabase(path: String, t: T): CompletableFuture<String?> =
        GlobalScope.future {
            api.pushToDatabase(path, t)
        }


    override fun updateChildToDatabase(
        path: String,
        value: Any
    ): CompletableFuture<DatabaseResult<Unit>> =
        GlobalScope.future {
            api.updateChildToDatabase(path, value)
        }

    override fun updateChildrenToDatabase(
        path: String,
        updates: HashMap<String, Any?>
    ): CompletableFuture<DatabaseResult<Unit>> =
        GlobalScope.future {
            api.updateChildrenToDatabase(path, updates)
        }

    override fun deleteFromDatabase(path: String): CompletableFuture<DatabaseResult<Unit>> =
        GlobalScope.future {
            api.deleteFromDatabase(path)
        }

    override fun getQueryFromDatabaseCache(query: Query) : CompletableFuture<T?> =
        GlobalScope.future {
            val result = api.getQueryFromDatabaseCache(query)
            convertDatabaseSnapshot(result.value)
        }

    override fun getQueryFromDatabase(query: Query): LiveData<T?> {
        val result = api.getQueryFromDatabase(query)
        val function = Function<DatabaseResult<DataSnapshot>, T> {
            if (it.isSuccess()) {
                convertDatabaseSnapshot(it.value)
            } else {
                null
            }
        }
        return Transformations.map(result, function)
    }

    abstract fun convertDatabaseSnapshot(value: DataSnapshot?): T?
}