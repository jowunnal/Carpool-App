package com.mate.carpool.ui.screen.home.vm

import com.mate.carpool.data.model.domain.TicketModel

data class BottomSheetUiState(
    val passengerId: Long,
    val studentId: String,
    val ticket: TicketModel
) {
    companion object {
        fun getInitValue() = BottomSheetUiState(
            passengerId = -1L,
            studentId = "",
            ticket = TicketModel.getInitValue()
        )
    }
}
