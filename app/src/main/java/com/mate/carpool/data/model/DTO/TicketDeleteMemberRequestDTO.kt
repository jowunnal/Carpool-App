package com.mate.carpool.data.model.DTO

data class TicketDeleteMemberRequestDTO(
    val passengerId:Long,
    val ticketId:Long,
    val isCancel:Int
)