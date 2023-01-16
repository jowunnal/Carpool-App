package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CarpoolListRepository {
    fun getTicketList() : Flow<ApiResponse<List<TicketListModel>>>
    fun getTicket(id:Long) :Flow<ApiResponse<TicketModel>>
    fun getMyTicket() : Flow<ApiResponse<TicketModel>>
}