package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.DriverRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.base.item.ResponseItem
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DriverRepositoryImpl @Inject constructor(
    private val apiService: APIService
): DriverRepository {

    override fun registerDriver(driverModel: DriverModel): Flow<ResponseItem> = handleFlowApi{
        apiService.registerDriver(
            image = driverModel.image,
            driverRegisterDTO = driverModel.asDriverRegisterRequestDTO()
        )
    }.map {
        when(it) {
            is ApiResponse.Loading -> { ResponseItem.getInitValue() }
            is ApiResponse.SuccessResponse -> {
                it.responseMessage.asResponseItem()
            }
            is ApiResponse.FailResponse -> {
                it.responseMessage.asResponseItem()
            }
            is ApiResponse.ExceptionResponse -> {
                ResponseItem(
                    status = it.e.printStackTrace().toString(),
                    message = it.e.message.toString()
                )
            }
        }
    }

}