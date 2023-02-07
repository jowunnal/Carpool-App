package com.mate.carpool.data.model.dto.dto.response

import com.mate.carpool.ui.base.item.ResponseItem

data class CommonResponse(
    val status: String,
    val message: String
) {
    fun asResponseItem() = ResponseItem(
        status = status,
        message = message
    )
}
