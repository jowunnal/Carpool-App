package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.TicketStatus
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface TicketChangeRepository {
    fun addNewPassengerToTicket(id:Long):Flow<ApiResponse<ResponseMessage>>
    fun updateTicketStatus(ticketId:Long,status:TicketStatus): Flow<ApiResponse<ResponseMessage>>
    fun deletePassengerToTicket(ticketId:Long,passengerId:Long):Flow<ApiResponse<ResponseMessage>>
}