package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.domain.ResponseModel
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun report(ticketId: String, userId: String, content: String): Flow<ResponseModel>

}