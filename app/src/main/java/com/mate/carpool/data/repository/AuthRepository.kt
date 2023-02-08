package com.mate.carpool.data.repository

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val autoLoginInfo:Flow<AutoLoginPreferences>

    fun login(
        email: String,
        passWord: String
    ): Flow<String>

    fun logout(): Flow<String>

    fun signUp(
        email: String,
        passWord: String,
        name: String
    ): Flow<String>

    fun checkAccessTokenIsExpired(): Flow<Result<ResponseMessage>>
}