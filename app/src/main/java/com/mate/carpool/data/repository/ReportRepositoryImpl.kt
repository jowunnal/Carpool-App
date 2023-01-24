package com.mate.carpool.data.repository

import com.mate.carpool.data.callApi
import com.mate.carpool.data.model.dto.request.ReportRequest
import com.mate.carpool.data.service.APIService
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : ReportRepository {

    override fun report(studentId: Long, content: String) = callApi {
        val body = ReportRequest(reportedStudentId = studentId.toString(), content = content)
        apiService.report(body)
    }

}