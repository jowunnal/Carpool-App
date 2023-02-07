package com.mate.carpool.ui.screen.home.item

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.item.DayStatus

data class TicketState(
    val id: Long,
    val studentNumber: String,
    val profileImage: String,
    val memberName: String,
    var startArea: String,
    val endArea: String,
    var boardingPlace: String,
    val dayStatus: DayStatus,
    var startTime: Long,
    var openChatUrl: String,
    var recruitPerson: Int,
    var ticketPrice: Int,
    val passenger: List<UserModel>
) {
    companion object {
        fun getInitValue() = TicketState(
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
            ticketPrice = 0,
            passenger = emptyList()
        )
    }
}