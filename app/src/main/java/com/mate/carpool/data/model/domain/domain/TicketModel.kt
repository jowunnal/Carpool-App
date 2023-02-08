package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.dto.dto.request.CreateTicketDTO
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.util.startTimeAsRequestDTO
import com.mate.carpool.util.startTimeToDayStatus

data class TicketModel(
    val id: Long,
    val profileImage: String,
    val memberName: String,
    val startArea: String,
    val endArea: String,
    val boardingPlace: String,
    val startTime: Long,
    val openChatUrl: String,
    val recruitPerson: Int,
    val ticketPrice: Int,
    val passenger: List<UserModel>
) {

    fun asTicketListState() = TicketListState(
        id = id,
        profileImage = profileImage,
        startArea = startArea,
        startTime = startTime,
        recruitPerson = recruitPerson,
        currentPersonCount = passenger.size,
        ticketStatus = TicketStatus.Before,
        dayStatus = startTime.startTimeToDayStatus()
    )

    companion object{
        fun getInitValue() = TicketModel(
            id = 0L,
            profileImage = "",
            memberName = "",
            startArea = "",
            endArea = "",
            boardingPlace = "",
            startTime = 0L,
            openChatUrl = "",
            recruitPerson = 0,
            ticketPrice = 0,
            passenger = emptyList()
        )
    }
}
