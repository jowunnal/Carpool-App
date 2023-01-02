package com.mate.carpool.data.repository

import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ResponseMessage
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
                    it.asDomain()
                }
                is ApiResponse.ExceptionResponse ->{
                    it.asDomain()
                }
            }
        }


    override fun getTicket() : Flow<Any> =
        handleFlowApi {
            apiService.getTicketPromise()
        }.map {
            when(it){
                is ApiResponse.SuccessResponse->{
                    it.responseMessage.asTicketDomain()
                }
                is ApiResponse.FailResponse -> {
                    it.asDomain()
                }
                is ApiResponse.ExceptionResponse ->{
                    it.asDomain()
                }
            }
        }

    fun TicketDetailResponseDTO.asTicketListDomain() = TicketListModel(
        this.profileImage,
        this.startArea,
        this.startTime,
        this.recruitPerson,
        1,
        this.ticketType,
        this.dayStatus
    )

    fun TicketDetailResponseDTO.asTicketDomain() = TicketModel(
        this.memberName,
        this.startArea,
        this.endArea,
        this.boardingPlace,
        this.startDayMonth,
        this.dayStatus,
        this.startTime,
        this.openChatUrl,
        this.recruitPerson,
        this.ticketType,
        this.ticketPrice
    )

    fun List<TicketDetailResponseDTO>.asListDomain() = map { it.asTicketListDomain() }

    fun ApiResponse.FailResponse.asDomain() = ResponseMessage(this.responseMessage.status,this.responseMessage.message,this.responseMessage.code)

    fun ApiResponse.ExceptionResponse.asDomain() = Throwable(this.e)

}