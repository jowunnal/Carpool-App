package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.data.model.domain.domain.ResponseModel
import com.mate.carpool.ui.base.item.ResponseItem
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface DriverRepository {
    fun registerDriver(
        carImage: MultipartBody.Part,
        carNumber: String,
        phoneNumber: String
    ): Flow<ResponseModel>
}