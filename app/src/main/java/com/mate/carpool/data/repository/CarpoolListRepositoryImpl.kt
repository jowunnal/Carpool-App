package com.mate.carpool.data.repository

import androidx.databinding.ObservableField
import com.mate.carpool.data.model.DTO.MemberResponseDTO
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.DTO.UserTicketDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import com.mate.carpool.ui.utils.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asMemberRoleToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartDayMonthToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketTypeToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarpoolListRepositoryImpl @Inject constructor(private val apiService: APIService) :CarpoolListRepository {

    override fun getTicketList() : Flow<ApiResponse<List<TicketListModel>>> =
        handleFlowApi {
            apiService.getTicketList()
        }.map {
            when(it){
                is ApiResponse.SuccessResponse->{
                    it.asTicketListDomain()
                }
                is ApiResponse.FailResponse -> {
                    ApiResponse.FailResponse(it.responseMessage)
                }
                is ApiResponse.ExceptionResponse -> {
                    ApiResponse.ExceptionResponse(it.e)
                }
            }
        }


    override fun getTicket(id:Long) : Flow<ApiResponse<TicketModel>> =
        handleFlowApi {
            apiService.getTicketReadId(id)
        }.map {
            when(it){
                is ApiResponse.SuccessResponse->{
                    it.asTicketDomain()
                }
                is ApiResponse.FailResponse -> {
                    ApiResponse.FailResponse(it.responseMessage)
                }
                is ApiResponse.ExceptionResponse -> {
                    ApiResponse.ExceptionResponse(it.e)
                }
            }
        }

    override fun getMyTicket(): Flow<ApiResponse<TicketModel>> =
        handleFlowApi {
        apiService.getMyTicket()
    }.map {
        when(it){
            is ApiResponse.SuccessResponse -> {
                it.asTicketDomain()
            }
            is ApiResponse.FailResponse -> {
                ApiResponse.FailResponse(it.responseMessage)
            }
            is ApiResponse.ExceptionResponse -> {
                ApiResponse.ExceptionResponse(it.e)
            }
        }
    }

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

    fun TicketDetailResponseDTO.asTicketDomain() = TicketModel(
        this.id,
        this.memberName,
        this.startArea,
        this.endArea,
        this.boardingPlace,
        this.startDayMonth.asStartDayMonthToDomain(),
        this.dayStatus.asDayStatusToDomain(),
        this.startTime.asStartTimeToDomain(),
        this.openChatUrl,
        this.recruitPerson,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketPrice,
        this.passengers?.asUserDomain()
    )

    fun List<MemberResponseDTO>.asUserDomain() = map { it.asUserDomain() }

    fun MemberResponseDTO.asUserDomain() = UserModel(
        this.memberName,
        this.studentNumber,
        this.department,
        ObservableField(this.phoneNumber),
        this.auth.asMemberRoleToDomain(),
        this.profileImage,
        emptyList(),
        this.passengerId
    )

    fun List<UserTicketDTO>.asTicketListDomain() = map { it.asTicketListDomain() }

    fun ApiResponse.SuccessResponse<List<UserTicketDTO>>.asTicketListDomain() = ApiResponse.SuccessResponse(this.responseMessage.asTicketListDomain())
    fun ApiResponse.SuccessResponse<TicketDetailResponseDTO>.asTicketDomain() = ApiResponse.SuccessResponse(this.responseMessage.asTicketDomain())
}