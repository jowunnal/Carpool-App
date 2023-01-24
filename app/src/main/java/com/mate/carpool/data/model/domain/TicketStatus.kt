package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable

@Stable
enum class TicketStatus(val displayName: String) {

    BEFORE(displayName = "BEFORE"),
    ING(displayName = "ING"),
    CANCEL(displayName = "CANCEL"),
    AFTER(displayName = "AFTER")
}