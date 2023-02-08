package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class LoginDTO(
    @SerializedName("email") val email: String,
    @SerializedName("password")val passWord: String
) {
    companion object {
        fun fromDomain(
            email: String,
            passWord: String
        ) = LoginDTO(
            email = email,
            passWord = passWord
        )
    }
}