package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.util.asDayStatusToDomain
import com.mate.carpool.util.asTicketStatusToDomain
import com.mate.carpool.util.asTicketTypeToDomain
import com.mate.carpool.util.formatStartTime

data class UserTicketDTO(
    val id:Long = 0,
    val profileImage:String = "",
    val startArea:String = "",
    val dayStatus:String = "",
    val startTime:String = "",
    val recruitPerson:Int = 0,
    val currentPersonCount:Int = 0,
    val ticketStatus:String = "",
    val ticketType: String=""
) {

    fun asTicketListDomain() = TicketListModel(
        this.id,
        this.profileImage,
        this.startArea,
        this.startTime.formatStartTime(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketStatus.asTicketStatusToDomain(),
        this.dayStatus.asDayStatusToDomain()
    )

}

