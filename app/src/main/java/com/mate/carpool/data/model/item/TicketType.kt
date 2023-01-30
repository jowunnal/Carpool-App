package com.mate.carpool.data.model.item

import androidx.compose.runtime.Stable

@Stable
enum class TicketType(val displayName: String) {
    Free(displayName = "유료"),
    Cost(displayName = "유료");
}

fun String.asTicketTypeDomain() =
    when (this) {
        "FREE" -> TicketType.Free
        "COST" -> TicketType.Cost
        else -> throw IllegalStateException("[TicketDto.toDomain] ticketType = $this")
    }
