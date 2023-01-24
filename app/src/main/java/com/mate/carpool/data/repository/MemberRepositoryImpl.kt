package com.mate.carpool.data.repository

import androidx.databinding.ObservableField
import com.google.gson.Gson
import com.mate.carpool.data.Result
import com.mate.carpool.data.callApi
import com.mate.carpool.data.model.DTO.MemberProfileDTO
import com.mate.carpool.data.model.DTO.UserTicketDTO
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import com.mate.carpool.ui.utils.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asMemberRoleToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketTypeToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val apiService: APIService
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

    fun MemberProfileDTO.asStatusDomain() = MemberModel(
        UserModel(
            this.memberName,
            this.studentNumber,
            this.department,
            ObservableField(this.phoneNumber),
            this.memberRole.asMemberRoleToDomain(),
            this.profileImage,
            emptyList(),
            -1
        ),
        this.tickets?.asTicketListDomain()
    )

    fun List<UserTicketDTO>.asTicketListDomain() = map { it.asTicketListDomain() }

    fun UserTicketDTO.asTicketListDomain() = TicketListModel(
        this.id,
        this.profileImage,
        this.startArea,
        this.startTime.asStartTimeToDomain(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketStatus.asTicketStatusToDomain(),
        this.dayStatus.asDayStatusToDomain()
    )

    override fun getMyProfile(): Flow<Result<Profile>> = callApi {
        apiService.getMyProfile().toDomain()
    }


    fun ApiResponse.SuccessResponse<MemberProfileDTO>.asStatusDomain() =
        ApiResponse.SuccessResponse(this.responseMessage.asStatusDomain())
}