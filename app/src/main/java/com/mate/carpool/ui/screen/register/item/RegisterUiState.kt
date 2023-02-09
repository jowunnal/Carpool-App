package com.mate.carpool.ui.screen.register.item

import androidx.compose.runtime.Stable
import okhttp3.MultipartBody

@Stable
data class RegisterUiState(
    val carImage: MultipartBody.Part?,
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