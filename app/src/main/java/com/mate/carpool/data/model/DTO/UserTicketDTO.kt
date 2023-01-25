package com.mate.carpool.data.model.DTO

import com.mate.carpool.data.model.domain.TicketListModel
import com.mate.carpool.ui.util.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.util.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.util.StringUtils.asTicketStatusToDomain
import com.mate.carpool.ui.util.StringUtils.asTicketTypeToDomain

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
        this.startTime.asStartTimeToDomain(),
        this.recruitPerson,
        this.currentPersonCount,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketStatus.asTicketStatusToDomain(),
        this.dayStatus.asDayStatusToDomain()
    )

}

