package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.domain.TicketModel
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CarpoolListRepositoryImpl @Inject constructor(private val apiService: APIService) :
    CarpoolListRepository {

    override fun getTicketList() : Flow<List<TicketModel>> = flow {
        emit(apiService.getTicketList())
    }.map { list ->
        list.map { ticketDTO ->
            ticketDTO.asTicketListDomainModel()
        }
    }.onEach { ticketList ->
        ticketList.sortedWith(compareBy { ticketModel ->
            ticketModel.available
        })
    }

    override fun getTicketById(id: String): Flow<TicketModel> = flow {
        emit(apiService.getTicketById(id))
    }.map { ticketDetail ->
        ticketDetail.asTicketDomainModel()
    }

    override fun getMyTicket(): Flow<TicketModel> = flow {
        emit(apiService.getMyTicket())
    }.map { ticketDetail ->
        ticketDetail.asTicketDomainModel()
    }
}