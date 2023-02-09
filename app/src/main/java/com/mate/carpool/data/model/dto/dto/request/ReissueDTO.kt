package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class ReissueDTO(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
) {
    companion object {
        fun fromDomain(
            accessToken: String,
            refreshToken: String
        ) = ReissueDTO(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
