package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.data.model.dto.dto.request.UpdateTicketDTO
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

    fun addNewPassengerToTicket(id: String): Flow<ResponseModel>
    fun deletePassengerToTicket(passengerId: String): Flow<ResponseModel>
    fun deleteMyTicket(ticketId: String): Flow<ResponseModel>
    fun updateTicketDetail(updateTicketDTO: UpdateTicketDTO): Flow<ResponseModel>
}