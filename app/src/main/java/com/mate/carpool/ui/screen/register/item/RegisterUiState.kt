package com.mate.carpool.ui.screen.register.item

import android.net.Uri
import com.mate.carpool.data.model.domain.domain.DriverModel
import okhttp3.MultipartBody

data class RegisterUiState(
    val carImage: Uri?,
    val carNumber: String,
    val phoneNumber: String,
    val invalidCarImage: Boolean,
    val invalidCarNumber: Boolean,
    val invalidPhoneNumber: Boolean
) {

    fun asDriverDomainModel(image: MultipartBody.Part) = DriverModel(
        image = image,
        carNumber = carNumber,
        phoneNumber = phoneNumber
    )

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