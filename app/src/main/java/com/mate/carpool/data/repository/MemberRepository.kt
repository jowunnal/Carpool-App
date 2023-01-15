package com.mate.carpool.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.ResponseMessage
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberRepository @Inject constructor(private val service: APIService) {

    fun checkIsDupStudentId(studentId: String) = flow {
        emit(Result.Loading)

        try {
            val result = service.checkIsStudentNumberExists(studentId)
            emit(Result.Success(result))

        } catch (e: Exception) {
            if (e is HttpException && e.code() == 409) {
                val response = Gson().fromJson(e.response()!!.errorBody()!!.string(), ResponseMessage::class.java)
                emit(Result.Error(response.message))

            } else {
                throw e
            }
        }
    }
}