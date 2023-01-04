package com.mate.carpool.data.repository

import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.DTO.UserTicketDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.service.APIService
import com.mate.carpool.data.utils.HandleFlowUtils.handleFlowApi
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
                is ApiResponse.FailResponse -> {
                }
                is ApiResponse.ExceptionResponse ->{
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
                is ApiResponse.FailResponse -> {
                }
                is ApiResponse.ExceptionResponse ->{
                }
            }
        }

    private fun UserTicketDTO.asTicketListDomain() = TicketListModel(
        this.id,
        this.profileImage,
        this.startArea,
        StringBuffer(this.startTime).insert(2,':').toString(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType,
        this.ticketStatus,
        this.dayStatus
    )

    private fun TicketDetailResponseDTO.asTicketDomain() = TicketModel(
        this.id,
        this.memberName,
        this.startArea,
        this.endArea,
        this.boardingPlace,
        StringBuffer(this.startDayMonth).insert(2,'/').toString(),
        this.dayStatus,StringBuffer(this.startTime).insert(2,':').toString(),
        this.openChatUrl,
        this.recruitPerson,
        this.ticketType,
        this.ticketPrice
    )


    private fun List<UserTicketDTO>.asListDomain() = map { it.asTicketListDomain() }

    /*
    fun ApiResponse.FailResponse.asDomain() = ResponseMessage(this.responseMessage.status,this.responseMessage.message,this.responseMessage.code)

    fun ApiResponse.ExceptionResponse.asDomain() = Throwable(this.e)
    */

}