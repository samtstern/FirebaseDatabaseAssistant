package com.sach.mark42.databasedemo

import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

@Keep
data class User(
    @Exclude @set:Exclude @get:Exclude
    var userPushId: String? = null,

    @get:PropertyName(DATABASE_KEY.USER.firstName)
    @set:PropertyName(DATABASE_KEY.USER.firstName)
    var firstName: String = "",

    @get:PropertyName(DATABASE_KEY.USER.lastName)
    @set:PropertyName(DATABASE_KEY.USER.lastName)
    var lastName: String = "",

    @get:PropertyName(DATABASE_KEY.USER.email)
    @set:PropertyName(DATABASE_KEY.USER.email)
    var email: String = ""
)