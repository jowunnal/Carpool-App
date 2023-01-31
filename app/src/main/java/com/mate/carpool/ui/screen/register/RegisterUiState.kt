package com.mate.carpool.ui.screen.register

import android.net.Uri

data class RegisterUiState(
    val carImage: Uri?,
    val carNumber: String,
    val phoneNumber: String,
    val invalidCarImage: Boolean,
    val invalidCarNumber: Boolean,
    val invalidPhoneNumber: Boolean
) {
    companion object {
        fun getInitValue() = RegisterUiState(
            carImage = null,
            carNumber = "",
            phoneNumber = "",
            invalidCarImage = false,
            invalidCarNumber = false,
            invalidPhoneNumber = false
        )
    }
}