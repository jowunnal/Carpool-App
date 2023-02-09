package com.mate.carpool.data.model.dto.dto.response

import com.mate.carpool.data.model.domain.domain.ResponseModel

data class CommonResponse(
    val status: String,
    val message: String
) {
    fun asResponseModel() = ResponseModel(
        status = status,
        message = message
    )
}
