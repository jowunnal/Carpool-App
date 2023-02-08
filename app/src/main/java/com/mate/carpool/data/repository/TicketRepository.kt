package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.ui.base.item.ResponseItem
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun createTicket(
        startArea: String,
        startTime: String,
        endArea: String,
        boardingPlace: String,
        openChatUrl: String,
        recruitPerson: Int,
        ticketPrice: Int
    ): Flow<ResponseModel>
    fun addNewPassengerToTicket(id:Long):Flow<ApiResponse<ResponseMessage>>
    fun updateTicketStatus(ticketId:Long,status: TicketStatus): Flow<ApiResponse<ResponseMessage>>
    fun deletePassengerToTicket(ticketId:Long,passengerId:Long):Flow<ApiResponse<ResponseMessage>>
}