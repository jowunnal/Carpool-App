package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.ui.base.item.ResponseItem
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun createTicket(ticketModel: TicketModel): Flow<ResponseItem>
    fun addNewPassengerToTicket(id:Long):Flow<ApiResponse<ResponseMessage>>
    fun updateTicketStatus(ticketId:Long,status: TicketStatus): Flow<ApiResponse<ResponseMessage>>
    fun deletePassengerToTicket(ticketId:Long,passengerId:Long):Flow<ApiResponse<ResponseMessage>>
}