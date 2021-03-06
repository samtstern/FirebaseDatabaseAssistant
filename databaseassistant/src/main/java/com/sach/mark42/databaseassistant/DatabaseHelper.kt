package com.sach.mark42.databaseassistant

import androidx.lifecycle.LiveData
import com.google.firebase.database.*
import java.util.HashMap
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseHelper<T> {
    suspend fun get(query: Query): DatabaseResult<DataSnapshot> = suspendCoroutine { coroutine ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                coroutine.resume(DatabaseResult.success(p0))
            }

            override fun onCancelled(error: DatabaseError) {
                coroutine.resume(DatabaseResult.failed(error.message))
            }
        })
    }

    suspend fun get(path: String): DatabaseResult<DataSnapshot> {
        val dataRef = FirebaseDatabase.getInstance().getReference(path)
        return get(dataRef)
    }

    suspend fun updateChild(path: String, value: Any) = suspendCoroutine<DatabaseResult<Unit>> { coroutine ->
        val dataRef = FirebaseDatabase.getInstance().getReference(path)
        dataRef.setValue(value).addOnSuccessListener {
            coroutine.resume(DatabaseResult.success(Unit))
        }.addOnFailureListener { e ->
            val message = e.message ?: ""
            coroutine.resume(DatabaseResult.failed(message))
        }
    }

    suspend fun delete(path: String) = suspendCoroutine<DatabaseResult<Unit>> { coroutine ->
        val dataRef = FirebaseDatabase.getInstance().getReference(path)
        dataRef.removeValue().addOnSuccessListener {
            coroutine.resume(DatabaseResult.success(Unit))
        }.addOnFailureListener { e ->
            val message = e.message ?: ""
            coroutine.resume(DatabaseResult.failed(message))
        }
    }

    suspend fun push(path: String, value: T) = suspendCoroutine<DatabaseResult<String>> { coroutine ->
        val dataRef = FirebaseDatabase.getInstance().getReference(path).push()
        dataRef.setValue(value).addOnSuccessListener {
            coroutine.resume(DatabaseResult.success(dataRef.key))
        }.addOnFailureListener { e ->
            val message = e.message ?: ""
            coroutine.resume(DatabaseResult.failed(message))
        }
    }

    suspend fun updateChildren(path: String, updates: HashMap<String, Any?>) =
        suspendCoroutine<DatabaseResult<Unit>> { coroutine ->
            val dataRef = FirebaseDatabase.getInstance().getReference(path)
            dataRef.updateChildren(updates).addOnSuccessListener {
                coroutine.resume(DatabaseResult.success(Unit))
            }.addOnFailureListener { e ->
                val message = e.message ?: ""
                coroutine.resume(DatabaseResult.failed(message))
            }
        }

    fun stream(path: String? = null, query: Query? = null): LiveData<DatabaseResult<DataSnapshot>> {
        return FirebaseQueryLiveData(path, query)
    }
}

private class FirebaseQueryLiveData(val path: String? = null, val query: Query? = null) : LiveData<DatabaseResult<DataSnapshot>>() {
    private val dataRef: Query
        get() {
            return if (path != null) {
                FirebaseDatabase.getInstance().getReference(path)
            } else {
                query!!
            }
        }
    private val listener = FirebaseValueEventListener()

    override fun onActive() {
        dataRef.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        dataRef.removeEventListener(listener)
        super.onInactive()
    }

    inner class FirebaseValueEventListener : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            value = DatabaseResult.failed(p0.message)
        }

        override fun onDataChange(p0: DataSnapshot) {
            value = DatabaseResult.success(p0)
        }
    }
}