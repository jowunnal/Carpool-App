package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.util.formatStartDayMonthToDTO
import com.mate.carpool.util.formatStartTimeToDTO

data class CreateCarpoolRequestDTO(
    var startArea:String = "",
    val endArea:String="경운대학교",
    var boardingPlace:String="",
    var startDayMonth:String="",
    var dayStatus:DayStatus ?= null,
    var startTime:String="",
    var openChatUrl: String="",
    var recruitPerson: Int=0,
    var ticketType: TicketType?= null,
    val ticketPrice: Int=0
){
    constructor(ticketModel: TicketModel):this(
        startArea = ticketModel.startArea,
        endArea = ticketModel.endArea,
        boardingPlace = ticketModel.boardingPlace,
        dayStatus = ticketModel.dayStatus,
        startTime = ticketModel.startTime.formatStartTimeToDTO(),
        startDayMonth = ticketModel.startTime.formatStartDayMonthToDTO(),
        openChatUrl = ticketModel.openChatUrl,
        recruitPerson = ticketModel.recruitPerson,
        ticketType = ticketModel.ticketType,
        ticketPrice = ticketModel.ticketPrice
    )
}