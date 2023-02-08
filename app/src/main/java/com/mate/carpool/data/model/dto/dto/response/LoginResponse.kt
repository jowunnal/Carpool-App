package com.mate.carpool.data.model.dto.dto.response

data class LoginResponse(
    val grantType:String,
    val accessToken:String,
    val refreshToken:String,
    val accessTokenExpiresIn:String,
    val refreshTokenExpiresIn:String
)
