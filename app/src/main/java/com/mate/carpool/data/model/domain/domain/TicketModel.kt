package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.ui.screen.home.item.PassengerState
import com.mate.carpool.ui.screen.home.item.TicketListState
import com.mate.carpool.ui.screen.home.item.TicketState
import com.mate.carpool.util.startTimeToDayStatus

data class TicketModel(
    val id: String,
    val profileImage: String,
    val startArea: String,
    val endArea: String,
    val boardingPlace: String,
    val startTime: Long,
    val openChatUrl: String,
    val recruitPerson: Int,
    val currentPerson: Int,
    val ticketPrice: Int,
    val available: Boolean,
    val driver: DriverModel,
    val passenger: List<UserModel>
) {

    fun asTicketListState() = TicketListState(
        id = id,
        profileImage = profileImage,
        startArea = startArea,
        startTime = startTime,
        recruitPerson = recruitPerson,
        currentPersonCount = passenger.size,
        dayStatus = startTime.startTimeToDayStatus(),
        available = available
    )

    fun asTicketState() = TicketState(
        id = id,
        startArea = startArea,
        endArea = endArea,
        boardingPlace = boardingPlace,
        dayStatus = startTime.startTimeToDayStatus(),
        startTime = startTime,
        openChatUrl = openChatUrl,
        recruitPerson = recruitPerson,
        ticketPrice = ticketPrice,
        driver = driver.asDriverState(),
        passenger = passenger.map { it.asUserStateItem() as PassengerState }

    )

    companion object {
        fun getInitValue() = TicketModel(
            id = "",
            profileImage = "",
            startArea = "",
            endArea = "",
            boardingPlace = "",
            startTime = 0L,
            openChatUrl = "",
            recruitPerson = 0,
            currentPerson = 0,
            ticketPrice = 0,
            available = false,
            driver = DriverModel.getInitValue(),
            passenger = emptyList()
        )
    }
}
