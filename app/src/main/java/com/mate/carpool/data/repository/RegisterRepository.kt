package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RegisterRepository {
    fun signUp(userModel: UserModel,image: MultipartBody.Part?) : Flow<ApiResponse<ResponseMessage>>
}