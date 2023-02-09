package com.mate.carpool.ui.screen.home.item

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.MemberRole

@Stable
data class TicketState(
    val id: String,
    var startArea: String,
    val endArea: String,
    var boardingPlace: String,
    val dayStatus: DayStatus,
    var startTime: Long,
    var openChatUrl: String,
    var recruitPerson: Int,
    var ticketPrice: Int,
    val driver: DriverState,
    val passenger: List<PassengerState>
) {
    companion object {
        fun getInitValue() = TicketState(
            id = "",
            startArea = "",
            endArea = "",
            boardingPlace = "",
            dayStatus = DayStatus.AM,
            startTime = 0L,
            openChatUrl = "",
            recruitPerson = 0,
            ticketPrice = 0,
            driver = DriverState.getInitValue(),
            passenger = emptyList()
        )
    }
}