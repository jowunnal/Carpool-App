package com.mate.carpool.data.repository

import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun getMemberInfo(): Flow<ApiResponse<MemberModel>>
    fun checkIsDupStudentId(studentId: String): Flow<Result<ResponseMessage>>
    fun getMyProfile(): Flow<Result<Profile>>
}

