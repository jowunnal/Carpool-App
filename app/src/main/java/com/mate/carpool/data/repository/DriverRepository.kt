package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.DriverModel
import com.mate.carpool.ui.base.item.ResponseItem
import kotlinx.coroutines.flow.Flow

interface DriverRepository {
    fun registerDriver(driverModel: DriverModel): Flow<ResponseItem>
}