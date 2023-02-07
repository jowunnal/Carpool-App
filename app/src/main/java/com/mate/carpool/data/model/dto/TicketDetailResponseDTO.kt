package com.mate.carpool.data.model.dto

import com.mate.carpool.data.model.domain.TicketModel
import com.mate.carpool.util.*
import com.mate.carpool.util.EncapsulationUtil.asUserDomain

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
        id = this.id,
        studentNumber = this.studentNumber,
        profileImage = this.profileImage,
        memberName = this.memberName,
        startArea = this.startArea,
        endArea = this.endArea,
        boardingPlace = this.boardingPlace,
        dayStatus = this.dayStatus.asDayStatusToDomain(),
        startTime = ("2301"+this.startDayMonth+this.startTime).formatStartTime(),
        openChatUrl = this.openChatUrl,
        recruitPerson = this.recruitPerson,
        ticketType = this.ticketType.asTicketTypeToDomain(),
        ticketPrice = this.ticketPrice,
        passenger = this.passengers?.asUserDomain() ?: emptyList()
    )
}