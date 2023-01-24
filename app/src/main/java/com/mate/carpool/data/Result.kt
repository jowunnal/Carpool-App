package com.mate.carpool.data

import android.util.Log
import com.google.gson.Gson
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


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

fun <T> callApi(action: suspend () -> T) = flow {
    emit(Result.Loading)

    try {
        val result = action()
        emit(Result.Success(result))

    } catch (e: Exception) {
        e.printStackTrace()

        if (e is HttpException) {
            Log.d("gumsil", e.response().toString())
            val response = try {
                Gson().fromJson(e.response()!!.errorBody()!!.string(), ResponseMessage::class.java)
            } catch (e: HttpException) {
                ResponseMessage(message = "알 수 없는 오류가 발생했습니다.", code = e.code().toString())
            }
            emit(Result.Error(response.message))

        } else {
            throw e
        }
    }
}