package com.firebase.mark42.databaseassistant

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import java.util.HashMap


class DatabaseApi<T> {

    suspend fun getFromDatabaseCache(path: String): DatabaseResult<DataSnapshot> {
        val databaseResult = DatabaseHelper<T>().get(path)

        if (databaseResult.value == null || !databaseResult.isSuccess()) {
            return DatabaseResult.failed(DatabaseResult.Error.DATABASE_REQUEST_FAILED.toString())
        }

        return databaseResult
    }

    fun getFromDatabase(path: String): LiveData<DatabaseResult<DataSnapshot>> {
        return DatabaseHelper<T>().stream(path = path)
    }

    suspend fun pushToDatabase(path: String, t: T): String? {
        val key = DatabaseHelper<T>().push(path, t)
        return key.value
    }

    suspend fun updateChildToDatabase(
        path: String,
        value: Any
    ): DatabaseResult<Unit> {
        return DatabaseHelper<T>().updateChild(path, value)
    }

    suspend fun updateChildrenToDatabase(
        path: String,
        updates: HashMap<String, Any?>
    ): DatabaseResult<Unit> {
        return DatabaseHelper<T>().updateChildren(path, updates)
    }

    suspend fun deleteFromDatabase(path: String): DatabaseResult<Unit> {
        return DatabaseHelper<T>().delete(path)
    }

    suspend fun getQueryFromDatabaseCache(query: Query): DatabaseResult<DataSnapshot> {
        val databaseResult = DatabaseHelper<T>().get(query)

        if (databaseResult.value == null || !databaseResult.isSuccess()) {
            return DatabaseResult.failed(DatabaseResult.Error.DATABASE_REQUEST_FAILED.toString())
        }

        return databaseResult
    }

    fun getQueryFromDatabase(query: Query): LiveData<DatabaseResult<DataSnapshot>> {
        return DatabaseHelper<T>().stream(query = query)
    }

    /*fun <T> convert(cls: Class<T>, snapShot: DataSnapshot): DatabaseResult<T?> {
        return try {

            val data = snapShot.getValue(cls)
            DatabaseResult.success(data)
        } catch (e: Exception) {
            val message = e.message ?: ""
            DatabaseResult.failed(message)
        }
    }

    inline fun <reified T> convertDatabaseSnapshot(snapShot: DataSnapshot): DatabaseResult<T?> {
        return convert(T::class.java, snapShot)
    }*/
}