package com.mate.carpool.ui.screen.createCarpool.item

import com.mate.carpool.data.model.domain.StartArea

data class CreateTicketUiState(
    val driverName: String,
    val driverProfile: String,
    val startArea: StartArea,
    val boardingPlace: String,
    val startTime: String,
    val openChatLink: String,
    val recruitNumber: String,
    val fee: String,
    val invalidArea: Boolean,
    val invalidStartTime: Boolean,
    val invalidOpenChatLink: Boolean,
    val invalidRecruitNumber: Boolean,
    val invalidFee: Boolean
) {
    companion object {
        fun getInitValue() = CreateTicketUiState(
            driverName = "홍길동",
            driverProfile = "",
            startArea = StartArea.ETC,
            boardingPlace = "",
            startTime = "",
            openChatLink = "",
            recruitNumber = "",
            fee = "",
            invalidArea = false,
            invalidStartTime = false,
            invalidOpenChatLink = false,
            invalidRecruitNumber = false,
            invalidFee = false
        )
    }
}