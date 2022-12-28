package com.mate.carpool.data.service

import com.mate.carpool.data.model.*
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

    @GET("member/check/class/{studentNumber}")
    fun checkIsStudentNumberExists(@Path("studentNumber") studentNumber:String) : Call<ResponseMessage>

    @POST("ticket/new")
    fun postTicketNew(@Body ticket:CarpoolTicketModel) : Call<ResponseMessage>
}
