package com.mate.carpool.data


sealed class Result<out R> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "in Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null