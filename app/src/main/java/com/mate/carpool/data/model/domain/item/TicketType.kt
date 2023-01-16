package com.mate.carpool.data.model.domain.item

enum class TicketType {
    Free,
    Cost
}

fun TicketType.getTicketType() =
    when(this){
        TicketType.Free -> "무료"
        TicketType.Cost -> "유료"
    }

