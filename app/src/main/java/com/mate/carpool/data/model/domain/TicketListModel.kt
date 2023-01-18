package com.mate.carpool.data.model.domain

import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.TicketStatus
import com.mate.carpool.data.model.domain.item.TicketType

/**
 * 티켓목록 Model
 */
data class TicketListModel(
    val id:Long = 0,
    val profileImage:String = "",
    val startArea:String = "",
    val startTime:String = "",
    val recruitPerson:Int = 0,
    val currentPersonCount:Int = 0,
    val ticketType:TicketType ?= null,
    val ticketStatus:TicketStatus ?= null,
    val dayStatus:DayStatus ?= null
)