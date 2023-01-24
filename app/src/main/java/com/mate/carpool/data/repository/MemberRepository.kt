package com.mate.carpool.data.repository

import com.mate.carpool.data.Result
import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.domain.Profile
import com.mate.carpool.data.model.domain.UserRole
import com.mate.carpool.data.model.response.ApiResponse
import com.mate.carpool.data.model.response.ResponseMessage
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek

interface MemberRepository {
    fun getMemberInfo(): Flow<ApiResponse<MemberModel>>
    fun checkIsDupStudentId(studentId: String): Flow<Result<ResponseMessage>>
    fun getMyProfile(): Flow<Result<Profile>>
    fun updateMyProfile(phone: String, userRole: UserRole, daysOfUse: List<DayOfWeek>): Flow<Result<ResponseMessage>>
}

