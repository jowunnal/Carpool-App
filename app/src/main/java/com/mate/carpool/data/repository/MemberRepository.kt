package com.mate.carpool.data.repository

import kotlinx.coroutines.flow.Flow


interface MemberRepository {
    fun getMemberInfo() : Flow<Any>
}