package com.mate.carpool.data.model.domain.item

enum class TicketStatus {
    Cancel,
    Before,
    Ing,
    After
}

fun TicketStatus.getTicketStatusDTO() =
    when(this){
        TicketStatus.Cancel -> "CANCEL"
        TicketStatus.After -> "AFTER"
        TicketStatus.Before -> "BEFORE"
        TicketStatus.Ing -> "ING"
    }