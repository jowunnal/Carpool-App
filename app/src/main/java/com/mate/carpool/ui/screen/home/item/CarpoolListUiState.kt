package com.mate.carpool.ui.screen.home.item

import androidx.compose.runtime.Stable

@Stable
data class CarpoolListUiState(
    val ticketList: List<TicketListState>,
    val carpoolExistState: Boolean,
    val userState: UserState,
    val refreshState: Boolean
) {
    companion object {
        fun getInitValue() = CarpoolListUiState(
            ticketList = emptyList(),
            carpoolExistState = false,
            userState = PassengerState.getInitValue(),
            refreshState = false
        )
    }
}