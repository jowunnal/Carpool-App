package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.data.model.dto.dto.request.CreateTicketDTO
import com.mate.carpool.data.model.dto.dto.request.UpdateTicketDTO
import com.mate.carpool.data.model.dto.dto.response.TicketDetailDTO
import com.mate.carpool.data.repository.TicketRepository
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(private val apiService: APIService) :
    TicketRepository {

    override fun createTicket(
        startArea: String,
        startTime: String,
        endArea: String,
        boardingPlace: String,
        openChatUrl: String,
        recruitPerson: Int,
        ticketPrice: Int
    ): Flow<ResponseModel> = flow {
        emit(
            apiService.createTicket(
                CreateTicketDTO.fromDomain(
                    startArea = startArea,
                    startTime = startTime,
                    endArea = endArea,
                    boardingPlace = boardingPlace,
                    openChatUrl = openChatUrl,
                    recruitPerson = recruitPerson,
                    ticketPrice = ticketPrice
                )
            )
        )
    }.map { response ->
        response.asResponseModel()
    }

    override fun addNewPassengerToTicket(id: String): Flow<ResponseModel> = flow {
        emit(apiService.addPassengerToTicket(id))
    }.map { response ->
        response.asResponseModel()
    }

    override fun updateTicketDetail(updateTicketDTO: UpdateTicketDTO): Flow<ResponseModel> = flow {
        emit(apiService.updateTicketDetail(updateTicketDTO))
    }.map { response ->
        response.asResponseModel()
    }

    override fun deletePassengerToTicket(passengerId: String): Flow<ResponseModel> = flow {
        emit(apiService.deleteUserFromTicket(passengerId))
    }.map { response ->
        response.asResponseModel()
    }

    override fun deleteMyTicket(ticketId: String): Flow<ResponseModel> = flow {
        emit(apiService.deleteMyTicket(ticketId))
    }.map { response ->
        response.asResponseModel()
    }

}