package com.mate.carpool.data.model.response

import com.mate.carpool.data.model.dto.dto.response.CommonResponse

sealed class ApiResponse<out T>{
    object Loading : ApiResponse<Nothing>()
    data class SuccessResponse<out T>(val responseMessage: T) : ApiResponse<T>()
    data class FailResponse(val responseMessage: CommonResponse) : ApiResponse<Nothing>()
    data class ExceptionResponse(val e:Throwable): ApiResponse<Nothing>()
}
