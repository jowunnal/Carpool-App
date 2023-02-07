package com.mate.carpool.data.model.domain.domain

import com.mate.carpool.data.model.domain.UserModel
import com.mate.carpool.data.model.dto.dto.request.CreateTicketRequestDTO
import com.mate.carpool.util.startTimeAsRequestDTO

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
    /*fun asTicketState() = TicketState(
        id = id,
        profileImage = profileImage,
        memberName = memberName,
        startArea = startArea,
        endArea = endArea,
        boardingPlace = boardingPlace,
        startTime = startTime,
        openChatUrl = openChatUrl,
        recruitPerson = recruitPerson,
        ticketPrice = ticketPrice,
        passenger = passenger
    )

     */

    fun asTicketRequestDTO() = CreateTicketRequestDTO(
        startArea = startArea,
        startTime = startTime.startTimeAsRequestDTO(),
        endArea = endArea,
        boardingPlace = boardingPlace,
        openChatUrl = openChatUrl,
        recruitPerson = recruitPerson,
        ticketPrice = ticketPrice
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
