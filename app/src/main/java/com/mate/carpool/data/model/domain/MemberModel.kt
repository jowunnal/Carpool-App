package com.mate.carpool.data.model.domain

/**
 * 맴버 정보를 담는 클래스
 * @param user 사용자정보
 * @param ticketList 사용자가 가진 티켓 정보
 */
data class MemberModel(
    val user : UserModel,
    val ticketList : List<TicketListModel> ?= emptyList()
) {
    companion object {
        fun getInitValue() = MemberModel(
            user = UserModel.getInitValue(),
            ticketList = emptyList()
        )
    }
}

