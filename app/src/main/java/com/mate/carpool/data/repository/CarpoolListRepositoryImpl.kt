package com.mate.carpool.data.repository

import androidx.databinding.ObservableField
import com.mate.carpool.data.model.DTO.MemberResponseDTO
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.DTO.UserTicketDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.ui.utils.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartDayMonthToDomain
import com.mate.carpool.ui.utils.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.utils.StringUtils.asTicketTypeToDomain
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarpoolListRepositoryImpl @Inject constructor(private val apiService: APIService) :CarpoolListRepository {

    override fun getTicketList() : Flow<Any> =
        handleFlowApi {
            apiService.getTicketList()
        }.map {
            when(it){
                is ApiResponse.SuccessResponse->{
                    it.responseMessage.asListDomain()
                }
                else -> {
                    it
                }
            }
        }


    override fun getTicket(id:Int) : Flow<Any> =
        handleFlowApi {
            apiService.getTicketReadId(id)
        }.map {
            when(it){
                is ApiResponse.SuccessResponse->{
                    it.responseMessage.asTicketDomain()
                }
                else -> {
                    it
                }
            }
        }

    override fun getMyTicket(): Flow<Any> = handleFlowApi {
        apiService.getMyTicket()
    }.map {
        when(it){
            is ApiResponse.SuccessResponse -> {
                it.responseMessage.asTicketDomain()
            }
            else -> {
                it
            }
        }
    }

    private fun UserTicketDTO.asTicketListDomain() = TicketListModel(
        this.id,
        this.profileImage,
        this.startArea,
        this.startTime.asStartTimeToDomain(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketStatus,
        this.dayStatus.asDayStatusToDomain()
    )

    private fun TicketDetailResponseDTO.asTicketDomain() = TicketModel(
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
        this.passengers?.asTicketDomain()
    )

    fun List<MemberResponseDTO>.asTicketDomain() = map { it.asTicketDomain() }

    fun MemberResponseDTO.asTicketDomain() = UserModel(
        this.memberName,
        this.studentNumber,
        this.department,
        ObservableField(this.phoneNumber),
        "studentType",
        this.profileImage,
        listOf("daycode"),
        this.passengerId
    )

    private fun List<UserTicketDTO>.asListDomain() = map { it.asTicketListDomain() }

    /*
    fun ApiResponse.FailResponse.asDomain() = ResponseMessage(this.responseMessage.status,this.responseMessage.message,this.responseMessage.code)

    fun ApiResponse.ExceptionResponse.asDomain() = Throwable(this.e)
    */

}