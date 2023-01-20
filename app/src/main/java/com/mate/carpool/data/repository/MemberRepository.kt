package com.mate.carpool.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
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

    fun checkIsDupStudentId(studentId: String) = callApi {
        service.checkIsStudentNumberExists(studentId)
    }

    fun getMyProfile() = callApi {
        service.getMyProfile()
    }
}

