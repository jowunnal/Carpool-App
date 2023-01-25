package com.mate.carpool.data

import android.util.Log
import com.google.gson.Gson
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


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

        when (e) {
            is HttpException -> {
                try {
                    val response: ResponseMessage = Gson().fromJson(
                        e.response()!!.errorBody()!!.string(),
                        ResponseMessage::class.java
                    )
                    emit(Result.Error(response.message))
                } catch (e: HttpException) {
                    emit(Result.Error(e.message()))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("Result", e.message, e)
                    emit(Result.Error(e.message ?: "알 수 없는 에러가 발생했습니다."))
                }
            }

            is IOException -> {
                emit(Result.Error("네트워크 확인 후 다시 시도해주세요."))
            }

            else -> {
                emit(Result.Error(e.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }

    }
}