package com.mate.carpool.ui.util

import com.mate.carpool.data.model.dto.dto.response.CommonResponse
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

object HandleFlowUtils {
    @JvmStatic
    fun <T : Any> handleFlowApi(
        execute: suspend () -> T,
    ): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)
        try {
            emit(ApiResponse.SuccessResponse(execute()))
        } catch (e: HttpException) {
            emit(ApiResponse.FailResponse(CommonResponse(e.code().toString(),e.message())))
        } catch (e: Exception) {
            emit(ApiResponse.ExceptionResponse(e = e))
        }
    }
}