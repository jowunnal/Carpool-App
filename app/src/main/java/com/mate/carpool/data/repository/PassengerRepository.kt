package com.mate.carpool.data.repository

import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface PassengerRepository {
    fun addNewPassengerToTicket(id:Int):Flow<ApiResponse<ResponseMessage>>
    fun deletePassengerToTicket(ticketId:Int,passengerId:Int):Flow<String>
}