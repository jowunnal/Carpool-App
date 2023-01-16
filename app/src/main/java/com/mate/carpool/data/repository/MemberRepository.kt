package com.mate.carpool.data.repository

import com.mate.carpool.data.model.domain.MemberModel
import com.mate.carpool.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow


interface MemberRepository {
    fun getMemberInfo() : Flow<ApiResponse<MemberModel>>
}