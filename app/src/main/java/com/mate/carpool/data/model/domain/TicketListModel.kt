package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable
import com.mate.carpool.data.model.item.DayStatus
import com.mate.carpool.data.model.item.TicketStatus
import com.mate.carpool.data.model.item.TicketType

/**
 * 티켓목록 Model
 */
@Stable
data class TicketListModel(
    val id:Long = 0,
    val profileImage:String = "",
    val startArea:String = "",
    val startTime:Long,
    val recruitPerson:Int = 0,
    val currentPersonCount:Int = 0,
    val ticketType: TicketType,
    val ticketStatus: TicketStatus,
    val dayStatus: DayStatus
) {
    companion object {
        fun getInitValue() = TicketListModel(
            id = -1L,
            profileImage = "",
            startArea = "",
            startTime = 0L,
            recruitPerson = 0,
            currentPersonCount = 0,
            ticketType = TicketType.Cost,
            ticketStatus = TicketStatus.Before,
            dayStatus = DayStatus.AM
        )
    }
}