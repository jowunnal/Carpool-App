package org.jinhostudy.swproject.api

import com.example.myapplication.data.model.LoginInfo
import com.example.myapplication.data.model.MemberInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface APIService {
    @POST("auth/login") // 엔드포인트부분
    fun postLogin(@Body loginInfo: LoginInfo) : Call<String>

    @GET("member/me")
    fun getMember():Call<MemberInfo>
}

