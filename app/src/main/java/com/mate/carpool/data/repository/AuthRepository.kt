package com.mate.carpool.data.repository

import com.mate.carpool.AutoLoginPreferences
import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.domain.UserModel
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val autoLoginInfo:Flow<AutoLoginPreferences>
    fun login(userModel: UserModel): Flow<String>
    fun logout(): Flow<String>
    fun signUp(userModel: UserModel): Flow<String>
    fun temporaryLogin(): Flow<Result<ResponseMessage>>
    fun checkAccessTokenIsExpired(): Flow<Result<ResponseMessage>>
}