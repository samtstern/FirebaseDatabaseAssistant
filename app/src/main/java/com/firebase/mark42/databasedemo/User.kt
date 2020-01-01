package com.firebase.mark42.databasedemo

import androidx.annotation.Keep

@Keep
data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = ""
)