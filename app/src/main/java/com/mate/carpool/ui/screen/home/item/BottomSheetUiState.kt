package com.mate.carpool.ui.screen.home.item

import androidx.compose.runtime.Stable

/**
 * @param ticket 개별 티켓의 상세정보
 * @param userTicket 사용자가 가진 티켓의 상세정보 ( 티켓을 가지고 있지 않다면 null )
 * @param ticketIsMineOrNot 티켓목록에서 티켓을 선택했을때 그티켓이 내티켓인지 아닌지의 결과값을 미리 가지고있는 정보
 * @param passengerId 선택된 패신저 id (퇴출하기 에 필요한 파라미터)
 * @param userId 선택된 사용자 id (신고하기 에 필요한 파라미터)
 */

@Stable
data class BottomSheetUiState(
    val ticket: TicketState,
    val userTicket: TicketState?,
    val ticketIsMineOrNot: Boolean,
    val passengerId: String,
    val userId: String
) {
    companion object {
        fun getInitValue() = BottomSheetUiState(
            ticket = TicketState.getInitValue(),
            userTicket = null,
            ticketIsMineOrNot = false,
            passengerId = "",
            userId = ""
        )
    }
}
