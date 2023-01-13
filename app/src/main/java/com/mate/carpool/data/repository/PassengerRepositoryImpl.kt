package com.mate.carpool.data.repository

import android.util.Log
import android.widget.Toast
import com.mate.carpool.data.model.DTO.TicketDeleteMemberRequestDTO
import com.mate.carpool.data.model.DTO.TicketNewMemberRequestDTO
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PassengerRepositoryImpl @Inject constructor(private val apiService: APIService) : PassengerRepository {

    override fun addNewPassengerToTicket(id:Int): Flow<ApiResponse<ResponseMessage>> = handleFlowApi{
        apiService.postPassengerNew(TicketNewMemberRequestDTO(id))
    }

    override fun deletePassengerToTicket(ticketId:Int,passengerId:Int): Flow<String> = handleFlowApi {
        apiService.deletePassenger(TicketDeleteMemberRequestDTO(passengerId,ticketId,0))
    }.map {
        when(it){
            is ApiResponse.SuccessResponse ->{
                "티켓 삭제가 완료되었습니다."
            }
            is ApiResponse.FailResponse -> {
                when(it.responseMessage.code){
                    "404"-> "해당 티켓을 찾을 수 없습니다."
                    else -> {it.responseMessage.message}
                }
            }
            is ApiResponse.ExceptionResponse ->{
                "일시적인 장애가 발생하였습니다. 재시도 해주세요. ${it.e.message}"
            }
        }
    }
}