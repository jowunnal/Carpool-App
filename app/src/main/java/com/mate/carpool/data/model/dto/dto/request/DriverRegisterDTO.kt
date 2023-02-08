package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class DriverRegisterDTO(
    @SerializedName("carNumber") val carNumber: String,
    @SerializedName("phoneNumber") val phoneNumber: String
) {
    companion object {
        fun fromDomain(
            carNumber: String,
            phoneNumber: String
        ) = DriverRegisterDTO(
            carNumber = carNumber,
            phoneNumber = phoneNumber
        )
    }
}
