package com.mate.carpool.data.model.dto

data class TicketDeleteMemberRequestDTO(
    val passengerId:Long,
    val ticketId:Long,
    val isCancel:Int
)