package com.sach.mark42.databaseassistant

class DatabaseResult<D>(
    val error: Error?,
    val value: D? = null,
    val errorMessage: String? = null
) {
    fun isSuccess(): Boolean = this.error == null

    companion object {
        fun <D> success(value: D?): DatabaseResult<D> = DatabaseResult(null, value)

        fun <D> failed(errorMessage: String) = DatabaseResult<D>(Error.DATABASE_REQUEST_FAILED, errorMessage = errorMessage)
    }

    enum class Error {
        DATABASE_REQUEST_FAILED,
        PERMISSION_DENIED,
        NOT_LOADED,
        NOT_EXIST
    }
}