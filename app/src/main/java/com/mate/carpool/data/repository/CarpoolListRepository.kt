package com.mate.carpool.data.repository

import kotlinx.coroutines.flow.Flow

interface CarpoolListRepository {
    fun getTicketList() : Flow<Any>
    fun getTicket(id:Int) :Flow<Any>
    fun getMyTicket() : Flow<Any>
}