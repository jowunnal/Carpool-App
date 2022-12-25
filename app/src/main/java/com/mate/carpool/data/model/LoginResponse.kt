package com.mate.carpool.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("grantType") val grantType:String,
                         @SerializedName("accessToken") val accessToken:String,
                         @SerializedName("refreshToken") val refreshToken:String,
                         @SerializedName("accessTokenExpiresIn") val accessTokenExpiresIn:Int,
                         @SerializedName("refreshTokenExpiresIn") val refreshTokenExpiresIn:Int):ResponseMessage()

abstract class ResponseMessage(val status:Int,
                               val message:String,
                               val code:String){
    constructor():this(0,"","")
}