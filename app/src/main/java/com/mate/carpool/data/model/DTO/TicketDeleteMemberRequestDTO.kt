package com.mate.carpool.data.model.DTO

data class TicketDeleteMemberRequestDTO(
    val passengerId:Int,
    val ticketId:Int,
    val isCancel:Int
)