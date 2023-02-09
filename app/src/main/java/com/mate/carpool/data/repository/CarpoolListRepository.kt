package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.TicketModel
import kotlinx.coroutines.flow.Flow

interface CarpoolListRepository {
    fun getTicketList() : Flow<List<TicketModel>>
    fun getTicketById(id: String) :Flow<TicketModel>
    fun getMyTicket() : Flow<TicketModel>
}