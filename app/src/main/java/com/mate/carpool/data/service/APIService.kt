package com.mate.carpool.data.service

import com.mate.carpool.data.model.LoginResponse
import com.mate.carpool.data.model.MemberRequestDTO
import com.mate.carpool.data.model.ResponseMessage
import com.mate.carpool.data.model.StudentInfo
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("auth/login")
    fun postLogin(@Body studentInfo: StudentInfo) : Call<LoginResponse>

    @GET("member/me")
    fun getMember():Call<String>

    @Multipart
    @POST("auth/signup")
    fun postSingUp(@Part("memberRequestDTO") memberRequestDTO: MemberRequestDTO, @Part image: MultipartBody.Part?):Call<ResponseMessage>
}
