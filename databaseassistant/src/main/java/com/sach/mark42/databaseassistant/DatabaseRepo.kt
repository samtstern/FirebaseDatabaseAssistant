package com.sach.mark42.databaseassistant

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.database.DataSnapshot
import androidx.arch.core.util.Function
import com.google.firebase.database.Query
import java.util.HashMap

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

    override suspend fun getFromDatabaseCache(path: String): T? {
        val result = api.getFromDatabaseCache(path)
        return convertDatabaseSnapshot(result.value)
    }

    override suspend fun pushToDatabase(path: String, t: T): String? {
        return api.pushToDatabase(path, t)
    }

    override suspend fun updateChildToDatabase(
        path: String,
        value: Any
    ): DatabaseResult<Unit> {
        return api.updateChildToDatabase(path, value)
    }

    override suspend fun updateChildrenToDatabase(
        path: String,
        updates: HashMap<String, Any?>
    ): DatabaseResult<Unit> {
        return api.updateChildrenToDatabase(path, updates)
    }

    override suspend fun deleteFromDatabase(path: String): DatabaseResult<Unit> {
        return api.deleteFromDatabase(path)
    }

    override suspend fun getQueryFromDatabaseCache(query: Query): T? {
        val result = api.getQueryFromDatabaseCache(query)
        return convertDatabaseSnapshot(result.value)
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