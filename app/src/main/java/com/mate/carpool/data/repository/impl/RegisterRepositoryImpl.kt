package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.RegisterRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val apiService: APIService) :
    RegisterRepository {

    override fun signUp(userModel: UserModel, image: MultipartBody.Part?): Flow<ApiResponse<ResponseMessage>> = handleFlowApi{
        apiService.postSignUp(userModel.asMemberRequestDTO(),image)?: ResponseMessage()
    }

}