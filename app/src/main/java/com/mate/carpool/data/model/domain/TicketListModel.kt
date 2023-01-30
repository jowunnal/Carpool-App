package com.mate.carpool.data.model.domain

import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.TicketType

/**
 * 티켓목록 Model
 */
data class TicketListModel(
    val id:Long = 0,
    val profileImage:String = "",
    val startArea:String = "",
    val startTime:Long,
    val recruitPerson:Int = 0,
    val currentPersonCount:Int = 0,
    val ticketType: TicketType,
    val ticketStatus: TicketStatus,
    val dayStatus:DayStatus ?= null
)