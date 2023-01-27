package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.CarpoolListRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.util.EncapsulationUtil.asTicketDomain
import com.mate.carpool.util.EncapsulationUtil.asTicketListDomain
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarpoolListRepositoryImpl @Inject constructor(private val apiService: APIService) :
    CarpoolListRepository {

    override fun getTicketList() : Flow<ApiResponse<List<TicketListModel>>> =
        handleFlowApi {
            apiService.getTicketList()
        }.map {
            when(it){
                is ApiResponse.Loading -> {
                    ApiResponse.Loading
                }
                is ApiResponse.SuccessResponse->{
                    it.asTicketListDomain()
                }
                is ApiResponse.FailResponse -> {
                    ApiResponse.FailResponse(it.responseMessage)
                }
                is ApiResponse.ExceptionResponse -> {
                    ApiResponse.ExceptionResponse(it.e)
                }
            }
        }


    override fun getTicket(id:Long) : Flow<ApiResponse<TicketModel>> =
        handleFlowApi {
            apiService.getTicketReadId(id)
        }.map {
            when(it){
                is ApiResponse.Loading -> {
                    ApiResponse.Loading
                }
                is ApiResponse.SuccessResponse->{
                    it.asTicketDomain()
                }
                is ApiResponse.FailResponse -> {
                    ApiResponse.FailResponse(it.responseMessage)
                }
                is ApiResponse.ExceptionResponse -> {
                    ApiResponse.ExceptionResponse(it.e)
                }
            }
        }

    override fun getMyTicket(): Flow<ApiResponse<TicketModel>> =
        handleFlowApi {
        apiService.getMyTicket()
    }.map {
        when(it){
            is ApiResponse.Loading -> {
                ApiResponse.Loading
            }
            is ApiResponse.SuccessResponse -> {
                it.asTicketDomain()
            }
            is ApiResponse.FailResponse -> {
                ApiResponse.FailResponse(it.responseMessage)
            }
            is ApiResponse.ExceptionResponse -> {
                ApiResponse.ExceptionResponse(it.e)
            }
        }
    }
}