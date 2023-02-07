package com.mate.carpool.ui.base.item

data class ResponseItem(
    val status: String,
    val message: String
) {
    companion object {
        fun getInitValue() = ResponseItem(
            status = "",
            message = ""
        )
    }
}
