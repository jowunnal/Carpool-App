package com.mate.carpool.ui.screen.home.item

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketStatus

@Stable
data class TicketListState(
    val id: String,
    val profileImage: String,
    val startArea: String,
    val startTime: Long,
    val recruitPerson: Int,
    val currentPersonCount: Int,
    val ticketStatus: TicketStatus,
    val dayStatus: DayStatus
) {
    companion object {
        fun getInitValue() = TicketListState(
            id = "",
            profileImage = "",
            startArea = "",
            startTime = 0L,
            recruitPerson = 0,
            currentPersonCount = 0,
            ticketStatus = TicketStatus.Before,
            dayStatus = DayStatus.AM
        )
    }
}