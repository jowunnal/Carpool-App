package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.domain.item.DayStatus
import com.mate.carpool.data.model.domain.item.TicketType

data class CreateCarpoolRequestDTO(
    var startArea:String = "",
    val endArea:String="경운대학교",
    var boardingPlace:String="",
    var startDayMonth:String="",
    var dayStatus:DayStatus ?= null,
    var startTime:String="",
    var openChatUrl: String="",
    var recruitPerson: Int=0,
    var ticketType: TicketType ?= null,
    val ticketPrice: Int=0
){
    constructor(ticketModel: TicketModel):this(
        ticketModel.startArea,
        ticketModel.endArea,
        ticketModel.boardingPlace,
        ticketModel.startDayMonth,
        ticketModel.dayStatus,
        ticketModel.startTime,
        ticketModel.openChatUrl,
        ticketModel.recruitPerson,
        ticketModel.ticketType,
        ticketModel.ticketPrice
    )
}