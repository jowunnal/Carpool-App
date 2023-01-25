package com.mate.carpool.data.service

import com.mate.carpool.data.model.*
import com.mate.carpool.data.model.DTO.*
import com.mate.carpool.data.model.domain.item.StudentItem
import com.mate.carpool.data.model.dto.ProfileDto
import com.mate.carpool.data.model.dto.TicketDeleteMemberRequestDTO
import com.mate.carpool.data.model.dto.request.ReportRequest
import com.mate.carpool.data.model.dto.request.UpdateMyProfileRequest
import com.mate.carpool.data.model.response.LoginResponse
import com.mate.carpool.data.model.response.ResponseMessage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @POST("auth/login")
    fun postLogin(@Body studentInfo: StudentItem): Call<LoginResponse>

    @Multipart
    @POST("auth/signup")
    suspend fun postSignUp(
        @Part("memberRequestDTO") memberRequestDTO: MemberRequestDTO,
        @Part image: MultipartBody.Part?
    ): ResponseMessage?

    @GET("member/check/class/{studentNumber}")
    suspend fun checkIsStudentNumberExists(
        @Path("studentNumber") studentNumber: String
    ): ResponseMessage

    @POST("ticket/new")
    fun postTicketNew(@Body ticket: CreateCarpoolRequestDTO): Call<ResponseMessage>

    @GET("ticket/read/{id}")
    suspend fun getTicketReadId(@Path("id") id: Long): TicketDetailResponseDTO

    @GET("ticket/update/{id}")
    fun getTicketUpdateId(
        @Path("id") id: Long,
        @Query("status") status: String
    ): Call<ResponseMessage>

    @GET("ticket/list")
    suspend fun getTicketList(): List<UserTicketDTO>

    @GET("ticket/promise")
    suspend fun getMyTicket(): TicketDetailResponseDTO

    @GET("member/me")
    suspend fun getMemberMe(): MemberProfileDTO

    @POST("passenger/new")
    suspend fun postPassengerNew(@Body ticketId: TicketNewMemberRequestDTO): ResponseMessage

    @HTTP(method = "DELETE", path = "passenger", hasBody = true)
    suspend fun deletePassenger(@Body ticket: TicketDeleteMemberRequestDTO): ResponseMessage

    @GET("member/me")
    suspend fun getMyProfile(): ProfileDto

    @PUT("member/update/profile")
    suspend fun updateMyProfile(@Body body: UpdateMyProfileRequest): ResponseMessage

    @POST("report/new")
    suspend fun report(@Body body: ReportRequest): ResponseMessage
}
