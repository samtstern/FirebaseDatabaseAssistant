package com.firebase.mark42.databaseassistant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.firebase.database.DataSnapshot
import androidx.arch.core.util.Function

abstract class DatabaseRepo<T>(): Repo<T> {

    private val api = DatabaseApi<T>()

    override var cached: T? = null

    override fun get(): T? {
        return cached
    }

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

    override suspend fun postToDatabase(path: String, t: T): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun convertDatabaseSnapshot(value: DataSnapshot?): T?
}