package com.mate.carpool.data.model.domain

import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.TicketType

/**
 * 개별티켓 Model
 */

data class TicketModel(
    var id:Long = 0,
    var memberName:String = "",
    var startArea:String = "",
    val endArea:String = "경운대학교",
    var boardingPlace:String = "",
    var startDayMonth:String = "",
    var dayStatus:DayStatus ?= null,
    var startTime:String = "",
    var openChatUrl: String = "",
    var recruitPerson: Int = 0,
    var ticketType: TicketType ?= null,
    val ticketPrice: Int = 0,
    val passenger:List<UserModel> ?= emptyList()
)
