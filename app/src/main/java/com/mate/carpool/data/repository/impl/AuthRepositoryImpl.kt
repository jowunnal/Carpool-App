package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.Result
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override fun login(email: String, password: String) = flow {
        when {
            email == "fail" -> {
                emit(
                    Result.Error("invalidEmail")
                )
            }

            password == "fail" -> {
                emit(
                    Result.Error("invalidPassword")
                )
            }

            else -> {
                emit(
                    Result.Success(ResponseMessage())
                )
            }
        }
    }
}