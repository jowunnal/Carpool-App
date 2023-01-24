package com.mate.carpool.data.repository

import com.mate.carpool.data.model.DTO.MemberRequestDTO
import com.mate.carpool.data.model.DTO.MemberTimeTableRequestDTO
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.domain.item.getMemberRoleDTO
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val apiService: APIService) : RegisterRepository{

    override fun signUp(userModel: UserModel, image: MultipartBody.Part?): Flow<ApiResponse<ResponseMessage>> = handleFlowApi{
        apiService.postSignUp(userModel.asMemberRequestDTO(),image)?: ResponseMessage()
    }

    fun UserModel.asMemberRequestDTO() = MemberRequestDTO(
        this.studentID,
        this.name,
        this.department,
        this.phone.replace("-",""),
        this.role.getMemberRoleDTO(),
        "",
        this.studentDayCodes.asTimeTableListDTO()
    )

    fun List<String>.asTimeTableListDTO() = List<MemberTimeTableRequestDTO>(this.size){
        MemberTimeTableRequestDTO(this[it])
    }
}