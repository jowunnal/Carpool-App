package com.mate.carpool.data.service

import com.mate.carpool.data.model.*
import com.mate.carpool.data.model.DTO.MemberRequestDTO
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.DTO.MemberProfileDTO
import com.mate.carpool.data.model.item.StudentItem
import com.mate.carpool.data.model.response.LoginResponse
import com.mate.carpool.data.model.response.ResponseMessage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("auth/login")
    fun postLogin(@Body studentInfo: StudentItem) : Call<LoginResponse>

    @GET("member/me")
    fun getMember():Call<String>

    @Multipart
    @POST("auth/signup")
    fun postSingUp(@Part("memberRequestDTO") memberRequestDTO: MemberRequestDTO, @Part image: MultipartBody.Part?):Call<ResponseMessage>

    @GET("member/check/class/{studentNumber}")
    fun checkIsStudentNumberExists(@Path("studentNumber") studentNumber:String) : Call<ResponseMessage>

    @POST("ticket/new")
    fun postTicketNew(@Body ticket: TicketModel) : Call<ResponseMessage>

    @GET("ticket/read/{id}")
    fun getTicketReadId(@Path("id") id:Int):Call<TicketDetailResponseDTO>

    @GET("ticket/update/{id}")
    fun getTicketUpdateId(@Path("id") id:Int,@Query("status") status:String):Call<ResponseMessage>

    @GET("ticket/list")
    suspend fun getTicketList():List<TicketDetailResponseDTO>

    @GET("ticket/promise")
    suspend fun getTicketPromise(): TicketDetailResponseDTO

    @GET("member/me")
    suspend fun getUserProfile(): MemberProfileDTO
}
