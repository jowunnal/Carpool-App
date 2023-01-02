package com.mate.carpool.data.utils

import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

object HandleFlowUtils {
    @JvmStatic
    fun <T : Any> handleFlowApi(
        execute: suspend () -> T,
    ): Flow<ApiResponse<T>> = flow {
        try {
            emit(ApiResponse.SuccessResponse(execute()))
        } catch (e: HttpException) {
            emit(ApiResponse.FailResponse(ResponseMessage(e.code(),e.message(),e.code().toString())))
        } catch (e: Exception) {
            emit(ApiResponse.ExceptionResponse(e = e))
        }
    }.flowOn(Dispatchers.IO)
}