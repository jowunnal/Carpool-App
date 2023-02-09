package com.mate.carpool.data.repository.impl

import com.mate.carpool.data.model.dto.dto.request.ReportRequest
import com.mate.carpool.data.repository.ReportRepository
import com.mate.carpool.data.service.APIService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : ReportRepository {

    override fun report(ticketId: String, userId: String, content: String) = flow {
        emit(
            apiService.report(
                ReportRequest.fromDomain(
                    ticketId = ticketId,
                    userId = userId,
                    content = content
                )
            )
        )
    }.map { response ->
        response.asResponseModel()
    }

}