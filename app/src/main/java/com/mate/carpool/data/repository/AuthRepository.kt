package com.mate.carpool.data.repository

import com.mate.carpool.data.Result
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<ResponseMessage>>
}