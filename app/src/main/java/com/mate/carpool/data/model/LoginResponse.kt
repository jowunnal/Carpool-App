package com.mate.carpool.data.model


data class LoginResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: String,
    val refreshTokenExpiresIn: String
) : ResponseMessage()

open class ResponseMessage(
    val status: Int = 0,
    val message: String = ""
) : MessageCode()

open class MessageCode(val code: String = "")
