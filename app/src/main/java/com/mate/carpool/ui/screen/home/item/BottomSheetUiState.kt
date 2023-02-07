package com.mate.carpool.ui.screen.home.item


data class BottomSheetUiState(
    val passengerId: Long,
    val studentId: String,
    val ticket: TicketState,
    val ticketIsMineOrNot: Boolean
) {
    companion object {
        fun getInitValue() = BottomSheetUiState(
            passengerId = -1L,
            studentId = "",
            ticket = TicketState.getInitValue(),
            ticketIsMineOrNot = false
        )
    }
}
