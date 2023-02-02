package com.mate.carpool.ui.screen.createCarpool.item

import com.mate.carpool.data.model.item.DayStatus

data class TimeUiState(
    val hour: Int,
    val min: Int,
    val dayStatus: DayStatus
) {
    companion object {
        fun getInitValue() = TimeUiState(
            hour = 0,
            min = 0,
            dayStatus = DayStatus.AM
        )
    }
}