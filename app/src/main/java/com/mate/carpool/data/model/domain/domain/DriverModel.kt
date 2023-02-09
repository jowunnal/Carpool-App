package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.dto.dto.request.DriverRegisterDTO
import com.mate.carpool.ui.screen.home.item.DriverState
import okhttp3.MultipartBody

data class DriverModel(
    val carImage: String,
    val carNumber: String,
    val phoneNumber: String,
    val profileImage: String,
    val name: String
) {
    fun asDriverRegisterRequestDTO() = DriverRegisterDTO(
        carNumber = carNumber,
        phoneNumber = phoneNumber
    )

    fun asDriverState() = DriverState(
        id = "",
        name = name,
        profileImage = profileImage,
        phoneNumber = phoneNumber,
        email = "",
        carImage = carImage,
        carNumber = carNumber
    )

    companion object {
        fun getInitValue() = DriverModel(
            carImage = "",
            carNumber = "",
            phoneNumber = "",
            profileImage = "",
            name = ""
        )
    }
}