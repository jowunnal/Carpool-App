package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.data.model.dto.TicketDeleteMemberRequestDTO
import com.mate.carpool.data.model.dto.TicketNewMemberRequestDTO
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.getTicketStatusDTO
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.base.item.ResponseItem
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(private val apiService: APIService) :
    TicketRepository {

    override fun createTicket(ticketModel: TicketModel): Flow<ResponseItem> = handleFlowApi {
        apiService.createTicket(ticketModel.asTicketRequestDTO())
    }.map {
        when(it) {
            is ApiResponse.Loading -> { ResponseItem.getInitValue() }
            is ApiResponse.SuccessResponse -> {
                it.responseMessage.asResponseItem()
            }
            is ApiResponse.FailResponse -> {
                it.responseMessage.asResponseItem()
            }
            is ApiResponse.ExceptionResponse -> {
                ResponseItem(
                    status = it.e.printStackTrace().toString(),
                    message = it.e.message.toString()
                )
            }
        }
    }

    override fun addNewPassengerToTicket(id:Long): Flow<ApiResponse<ResponseMessage>> = handleFlowApi{
        apiService.postPassengerNew(TicketNewMemberRequestDTO(id))
    }

    override fun updateTicketStatus(ticketId: Long, status: TicketStatus): Flow<ApiResponse<ResponseMessage>> = handleFlowApi {
        apiService.getTicketUpdateId(ticketId,status.getTicketStatusDTO())
    }

    override fun deletePassengerToTicket(ticketId:Long,passengerId:Long): Flow<ApiResponse<ResponseMessage>> = handleFlowApi {
        apiService.deletePassenger(TicketDeleteMemberRequestDTO(passengerId,ticketId,0))
    }

}