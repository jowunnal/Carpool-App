package com.mate.carpool.data.repository

import com.mate.carpool.data.model.DTO.TicketDetailResponseDTO
import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface CarpoolListRepository {
    fun getTicketList() : Flow<Any>
    fun getTicket() :Flow<Any>
}