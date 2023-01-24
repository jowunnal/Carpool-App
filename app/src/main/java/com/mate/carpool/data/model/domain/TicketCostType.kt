package com.mate.carpool.data.model.domain

import androidx.compose.runtime.Stable

@Stable
enum class TicketCostType(val displayName: String) {

    FREE(displayName = "무료"),
    COST(displayName = "유료")
}