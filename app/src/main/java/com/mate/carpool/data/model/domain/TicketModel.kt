package com.mate.carpool.data.model.domain

import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketType

/**
 * 개별티켓 Model
 */

data class TicketModel(
    var id:Long = 0,
    var studentNumber: String = "",
    var profileImage: String = "",
    var memberName:String = "",
    var startArea:String = "",
    val endArea:String = "경운대학교",
    var boardingPlace:String = "",
    var dayStatus:DayStatus ?= null,
    var startTime:Long,
    var openChatUrl: String = "",
    var recruitPerson: Int = 0,
    var ticketType: TicketType?= null,
    val ticketPrice: Int = 0,
    val passenger:List<UserModel> ?= emptyList()
) {
    companion object{
        fun getInitValue() = TicketModel(
            id = 0L,
            studentNumber = "",
            profileImage = "",
            memberName = "",
            startArea = "",
            endArea = "",
            boardingPlace = "",
            dayStatus = DayStatus.Morning,
            startTime = 0L,
            openChatUrl = "",
            recruitPerson = 0,
            ticketType = TicketType.Cost,
            ticketPrice = 0,
            passenger = emptyList()
        )
    }
}
