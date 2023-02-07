package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.dto.dto.request.DriverRegisterDTO
import okhttp3.MultipartBody

data class DriverModel(
    val image: MultipartBody.Part,
    val carNumber: String,
    val phoneNumber: String
) {
    fun asDriverRegisterRequestDTO() = DriverRegisterDTO(
        carNumber = carNumber,
        phoneNumber = phoneNumber
    )
}