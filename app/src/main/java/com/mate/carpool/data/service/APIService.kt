package com.mate.carpool.data.service

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("auth/login")
    fun postLogin() : Call<String>

    @GET("member/me")
    fun getMember():Call<String>
}
