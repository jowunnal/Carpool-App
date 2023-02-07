package com.mate.carpool.data.model.domain

import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketType
import com.mate.carpool.ui.screen.home.item.TicketState

/**
 * 개별티켓 Model
 */

data class TicketModel(
    var id:Long = 0,
    var studentNumber: String = "",
    var profileImage: String = "",
    var memberName:String = "",
    var startArea:String = "",
    val endArea:String = "",
    var boardingPlace:String = "",
    var dayStatus: DayStatus,
    var startTime:Long,
    var openChatUrl: String = "",
    var recruitPerson: Int = 0,
    var ticketType: TicketType,
    val ticketPrice: Int = 0,
    val passenger:List<UserModel>
) {
    fun asTicketState() = TicketState(
        id = id,
        studentNumber = studentNumber,
        profileImage = profileImage,
        memberName = memberName,
        startArea = startArea,
        endArea = endArea,
        boardingPlace = boardingPlace,
        dayStatus = dayStatus,
        startTime = startTime,
        openChatUrl = openChatUrl,
        recruitPerson = recruitPerson,
        ticketPrice = ticketPrice,
        passenger = passenger
    )

    companion object{
        fun getInitValue() = TicketModel(
            id = 0L,
            studentNumber = "",
            profileImage = "",
            memberName = "",
            startArea = "",
            endArea = "",
            boardingPlace = "",
            dayStatus = DayStatus.AM,
            startTime = 0L,
            openChatUrl = "",
            recruitPerson = 0,
            ticketType = TicketType.Cost,
            ticketPrice = 0,
            passenger = emptyList()
        )
    }
}
