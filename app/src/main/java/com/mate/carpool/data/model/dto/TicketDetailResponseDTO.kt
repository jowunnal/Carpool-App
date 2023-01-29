package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.util.EncapsulationUtil.asUserDomain
import com.mate.carpool.ui.util.StringUtils.asDayStatusToDomain
import com.mate.carpool.ui.util.StringUtils.asStartDayMonthToDomain
import com.mate.carpool.ui.util.StringUtils.asStartTimeToDomain
import com.mate.carpool.ui.util.StringUtils.asTicketTypeToDomain

data class TicketDetailResponseDTO(
    val id:Long = 0,
    val studentNumber: String = "",
    val memberName:String = "",
    val profileImage:String = "",
    var startArea:String = "",
    val endArea:String = "경운대학교",
    var boardingPlace:String = "",
    var startDayMonth:String = "",
    var dayStatus:String = "",
    var startTime:String = "",
    var openChatUrl: String = "",
    var recruitPerson: Int = 0,
    var ticketType: String = "",
    val ticketPrice: Int = 0,
    val passengers:List<MemberResponseDTO>? = null
) {
    fun asTicketDomain() = TicketModel(
        this.id,
        this.studentNumber,
        this.memberName,
        this.startArea,
        this.endArea,
        this.boardingPlace,
        this.startDayMonth.asStartDayMonthToDomain(),
        this.dayStatus.asDayStatusToDomain(),
        this.startTime.asStartTimeToDomain(),
        this.openChatUrl,
        this.recruitPerson,
        this.ticketType.asTicketTypeToDomain(),
        this.ticketPrice,
        this.passengers?.asUserDomain()
    )
}