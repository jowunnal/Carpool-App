package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.data.model.dto.dto.request.DriverRegisterDTO
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.repository.DriverRepository
import com.mate.carpool.data.service.APIService
import com.mate.carpool.ui.base.item.ResponseItem
import com.mate.carpool.ui.util.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class DriverRepositoryImpl @Inject constructor(
    private val apiService: APIService
): DriverRepository {

    override fun registerDriver(
        carImage: MultipartBody.Part,
        carNumber: String,
        phoneNumber: String
    ): Flow<ResponseModel> = flow {
        emit(
            apiService.registerDriver(
                image = carImage,
                driverRegisterDTO = DriverRegisterDTO.fromDomain(
                    carNumber = carNumber,
                    phoneNumber = phoneNumber
                )
            )
        )
    }.map { response ->
        response.asResponseModel()
    }

}