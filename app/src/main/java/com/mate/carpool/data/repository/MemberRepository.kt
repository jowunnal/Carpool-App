package com.mate.carpool.data.repository

import kotlinx.coroutines.flow.Flow


interface MemberRepository {
    fun getMemberRole() : Flow<Any>
}