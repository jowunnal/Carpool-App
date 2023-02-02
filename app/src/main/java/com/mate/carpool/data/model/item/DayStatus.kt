package com.mate.carpool.data.model.item

import androidx.compose.runtime.Stable

@Stable
enum class DayStatus(val displayName: String) {
    AM(displayName = "오전"),
    PM(displayName = "오후")
}