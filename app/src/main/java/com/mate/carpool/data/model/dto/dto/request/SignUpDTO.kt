package com.mate.carpool.data.model.dto.dto.request

import com.google.gson.annotations.SerializedName

data class SignUpDTO(
    @SerializedName("email") val email: String,
    @SerializedName("username") val userName: String,
    @SerializedName("password")val passWord: String
) {
    companion object {
        fun fromDomain(
            email: String,
            passWord: String,
            name: String
        ) = SignUpDTO(
            email = email,
            passWord = passWord,
            userName = name
        )
    }
}