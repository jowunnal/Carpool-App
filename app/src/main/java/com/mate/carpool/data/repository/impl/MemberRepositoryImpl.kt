package com.mate.carpool.data.repository.impl

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.data.model.dto.request.UpdateMyProfileRequest
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.MemberRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.util.EncapsulationUtil.asStatusDomain
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import com.mate.carpool.util.asMultipart
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.time.DayOfWeek
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    @ApplicationContext private val applicationContext: Context
) : MemberRepository {

    override fun getMemberInfo(): Flow<ApiResponse<MemberModel>> = handleFlowApi {
        apiService.getMemberMe()
    }.map {
        when (it) {
            is ApiResponse.Loading -> {
                ApiResponse.Loading
            }

            is ApiResponse.SuccessResponse -> {
                it.asStatusDomain()
            }

            is ApiResponse.FailResponse -> {
                ApiResponse.FailResponse(it.responseMessage)
            }

            is ApiResponse.ExceptionResponse -> {
                ApiResponse.ExceptionResponse(it.e)
            }
        }
    }

    override fun checkIsDupStudentId(studentId: String): Flow<Result<ResponseMessage>> = flow {
        emit(Result.Loading)

        try {
            val result = apiService.checkIsStudentNumberExists(studentId)
            emit(Result.Success(result))

        } catch (e: Exception) {
            if (e is HttpException && e.code() == 409) {
                val response = Gson().fromJson(
                    e.response()!!.errorBody()!!.string(),
                    ResponseMessage::class.java
                )
                emit(Result.Error(response.message))

            } else {
                throw e
            }
        }
    }

    override fun getMyProfile(): Flow<Result<Profile>> = callApi {
        apiService.getMyProfile().toDomain()
    }

    override fun updateProfile(
        phone: String,
        userRole: UserRole,
        daysOfUse: List<DayOfWeek>
    ): Flow<Result<ResponseMessage>> = callApi {
        val body = UpdateMyProfileRequest.fromDomain(
            phone = phone,
            userRole = userRole,
            daysOfUse = daysOfUse
        )
        apiService.updateProfile(body)
    }

    override fun updateProfileImage(uri: Uri): Flow<Result<ResponseMessage>> = callApi {
        val image = uri.asMultipart(
            name = "image",
            contentResolver = applicationContext.contentResolver
        )
        apiService.updateProfileImage(image = image)
    }
}