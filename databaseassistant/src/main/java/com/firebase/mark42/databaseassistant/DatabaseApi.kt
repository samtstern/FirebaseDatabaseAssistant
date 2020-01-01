package com.firebase.mark42.databaseassistant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query
import java.lang.reflect.ParameterizedType
import java.util.HashMap
import java.lang.reflect.Type


class DatabaseApi<T> {

    suspend fun getFromDatabaseCache(path: String): DatabaseResult<DataSnapshot> {
        val databaseResult = DatabaseHelper().get(path)
        if (databaseResult.value == null || !databaseResult.isSuccess()) {
            return DatabaseResult.failed(DatabaseResult.Error.DATABASE_REQUEST_FAILED.toString())
        }

        return try {
            databaseResult
        } catch (e: Exception) {
            DatabaseResult.failed(e.toString())
        }
    }

    fun getFromDatabase(path: String): LiveData<DatabaseResult<DataSnapshot>> {
        val stream = DatabaseHelper().stream(path)
        return stream
    }

    suspend fun pushToDatabase(path: String, t: T): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun postToDatabase(path: String, t: T): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    suspend fun updateChildToDatabase(
        path: String,
        childPath: String,
        value: Any
    ): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun updateChildrenToDatabase(
        path: String,
        updates: HashMap<String, Any?>
    ): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun deleteFromDatabase(path: String): DatabaseResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    suspend fun getQueryFromDatabaseCache(query: Query): DatabaseResult<T?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getQueryFromDatabase(query: Query): LiveData<T?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}